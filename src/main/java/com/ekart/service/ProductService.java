package com.ekart.service;

import com.ekart.constants.Constants;
import com.ekart.dao.*;
import com.ekart.dao.specs.*;
import com.ekart.dto.BrandDto;
import com.ekart.dto.CategoryDto;
import com.ekart.dto.SizeDto;
import com.ekart.dto.SubCategoryDto;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    public List<Product> getAllProducts(Map<String, Object> map) {
        ProductSpecification specs = getProductSpecification(map);
        Integer pageNo = map.get(Constants.PAGE_NO) == null ? 0 : (Integer) map.get(Constants.PAGE_NO);
        Integer pageSize = map.get(Constants.PAGE_SIZE) == null ? 10 : (Integer) map.get(Constants.PAGE_SIZE);
        String sortBy = map.get(Constants.SORT_BY) == null ? "name" : (String) map.get(Constants.SORT_BY);
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Product> productPage = productRepository.findAll(specs, pageable);
        if(productPage.hasContent()) {
            return productPage.getContent();
        } else {
            return new ArrayList<Product>();
        }
    }

    private ProductSpecification getProductSpecification(Map<String, Object> map) {
        ProductSpecification specification = new ProductSpecification();
        String categoryName = (String) map.get(Constants.CATEGORY);
        String subCategoryName = (String) map.get(Constants.SUBCATEGORY);
        List<String> brandsList = (List<String>) map.get(Constants.BRAND);
        Integer minPrice = (Integer) map.get(Constants.MIN_PRICE);
        Integer maxPrice = (Integer) map.get(Constants.MAX_PRICE);
        List<String> sizeList = (List<String>) map.get(Constants.SIZE);
        List<String> colorList = (List<String>) map.get(Constants.COLOR);

        if(categoryName != null && !categoryName.isEmpty()) {
            Optional<Category> category = categoryRepository.findCategoryByName(categoryName);
            if(category.isPresent()) {
                specification.add(new SearchCriteria(Constants.CATEGORY, category.get(), SearchOperation.EQUAL));
            }
        }

        if(subCategoryName != null && !subCategoryName.isEmpty()) {
            Optional<SubCategory> subCategory = subCategoryRepository.findSubCategoryByName(subCategoryName);
            if(subCategory.isPresent()) {
                specification.add(new SearchCriteria(Constants.SUB_CATEGORY, subCategory.get(), SearchOperation.EQUAL));
            }
        }

        if(brandsList != null && !brandsList.isEmpty()) {
            List<Brand> requestedBrands = new ArrayList<>();
            brandsList.forEach((brandName) -> {
                Optional<Brand> brand = brandRepository.findBrandByName(brandName);
                if(brand.isPresent()) {
                    requestedBrands.add(brand.get());
                }
            });
            specification.add(new SearchCriteria(Constants.BRAND, requestedBrands, SearchOperation.IN));
        }

        if(minPrice != null) {
            specification.add(new SearchCriteria(Constants.PRICE, minPrice, SearchOperation.GREATER_THAN_EQUAL));
        }

        if(maxPrice != null) {
            specification.add(new SearchCriteria(Constants.PRICE, maxPrice, SearchOperation.LESS_THAN_EQUAL));
        }

        if(sizeList != null && !sizeList.isEmpty()) {
            Set<Size> requestedSizes = new HashSet<>();
            sizeList.forEach((sizeName) -> {
                Optional<Size> size = sizeRepository.findSizeBySize(sizeName);
                if(size.isPresent()) {
                    requestedSizes.add(size.get());
                }
            });
            specification.add(new SearchCriteria(Constants.SIZE, requestedSizes, SearchOperation.IN));
        }

        if(colorList != null && !colorList.isEmpty()) {
            List<Color> colors = new ArrayList<>();
            colorList.forEach(color -> {
                colors.add(Color.valueOf(color));
            });
            specification.add(new SearchCriteria(Constants.COLOR, colors, SearchOperation.IN));
        }
        return specification;
    }

    public List<byte[]> getProductImagesById(Long id) {
        List<byte[]> images = productRepository.getProductImagesById(id);
        return images.size() > 0 ? images : new ArrayList<byte[]>();
    }

    public Product getProductById(Long id) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RecordNotFoundException("No product exists for given id " + id);
        }
    }

    public Product saveProduct(Map<String, Object> productMap) {
        Category newCategory;
        SubCategory newSubCategory;
        Brand newBrand;
        Size newSize = null;
        List<String> desc = new ArrayList<>();

        String categoryName = (String) productMap.get(Constants.CATEGORY);
        Optional<Category> category = categoryRepository.findCategoryByName(categoryName);
        if(!category.isPresent()) {
            newCategory = new Category();
            newCategory.setName(categoryName);
        } else {
            newCategory = category.get();
        }
        String subCategoryName = (String) productMap.get(Constants.SUBCATEGORY);
        Optional<SubCategory> subCategory = subCategoryRepository.findSubCategoryByName(subCategoryName);
        if(!subCategory.isPresent()) {
            newSubCategory = new SubCategory();
            newSubCategory.setName(subCategoryName);
            newSubCategory.setCategory(newCategory);
        } else {
            newSubCategory = subCategory.get();
        }
        String brandName = (String) productMap.get(Constants.BRAND);
        Optional<Brand> brand = brandRepository.findBrandByName(brandName);
        if(!brand.isPresent()) {
            newBrand = new Brand();
            newBrand.setName(brandName);
        } else {
            newBrand = brand.get();
        }
        String sizeName = (String) productMap.get(Constants.SIZE);
        if(sizeName != null) {
            Optional<Size> size = sizeRepository.findSizeBySize(sizeName);
            if(!size.isPresent()) {
                newSize = new Size();
                newSize.setSize(sizeName);
            } else {
                newSize = size.get();
            }
        }
        String description = (String) productMap.get(Constants.DESCRIPTION);
        if(description != null) {
            desc = Arrays.stream(description.split("\n")).collect(Collectors.toList());
        }
        String color = (String) productMap.get(Constants.COLOR);
        Color newColor = Color.valueOf(color);
        byte[] thumbnailImage = ((String) productMap.get(Constants.THUMBNAIL)).getBytes();
        List<byte[]> images = ((List<String>) productMap.get(Constants.IMAGES))
                                .stream().map(data -> data.getBytes()).collect(Collectors.toList());
        String name = (String) productMap.get(Constants.NAME);
        BigDecimal price = new BigDecimal((Integer) productMap.get(Constants.PRICE));
        Integer quantity = (Integer) productMap.get(Constants.QUANTITY);
        Double discount = ((Integer) productMap.get(Constants.DISCOUNT)).doubleValue();
        Product product = new Product(name, newCategory, newSubCategory, newBrand, newSize, newColor, desc,
                price, discount, quantity, thumbnailImage,  images);
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No product exists for given id " + id);
        }
    }

    /**
     * Returns the products filters data
     * @param map
     * @return
     */
    public Map<String, Object> getFilterCriteria(Map<String, Object> map) {
        Map<String, Object> returnMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        Specification specification = getProductSpecification(map);

        // Get Product max price
        CriteriaQuery<Number> criteriaQuery = criteriaBuilder.createQuery(Number.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        criteriaQuery.select(criteriaBuilder.max(productRoot.get(Constants.PRICE)));
        criteriaQuery.where(specification.toPredicate(productRoot, criteriaQuery, criteriaBuilder));
        Number returnMaxPrice = em.createQuery(criteriaQuery).getSingleResult();
        returnMap.put(Constants.MAX_PRICE, returnMaxPrice);

        List<CategoryDto> categoryResponse = getCategoryFilters(map, criteriaBuilder, specification);
        returnMap.put(Constants.CATEGORY, categoryResponse);

        List<SubCategoryDto> subCategoryResponse = getSubCategoryFilters(map, criteriaBuilder, specification);
        returnMap.put(Constants.SUBCATEGORY, subCategoryResponse);

        List<BrandDto> brandsResponse = getBrandFilters(map, criteriaBuilder, specification);
        returnMap.put(Constants.BRAND, brandsResponse);

        List<SizeDto> sizesResponse = getSizeFilters(map, criteriaBuilder, specification);
        returnMap.put(Constants.SIZE, sizesResponse);

        List<Color> colorResponse = getColorFilters(criteriaBuilder, specification);
        returnMap.put(Constants.COLOR, colorResponse);

        return returnMap;
    }

    /**
     * Forms the dynamic query critera for product filters
     * @param criteriaBuilder
     * @param specification
     * @param className
     * @param entityName
     * @return
     */
    private CriteriaQuery<?> getCriteriaQuery(CriteriaBuilder criteriaBuilder, Specification specification, Class<?> className, String entityName) {
        CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery(className);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        criteriaQuery.select(productRoot.get(entityName)).distinct(true);
        criteriaQuery.where(specification.toPredicate(productRoot, criteriaQuery, criteriaBuilder));
        return criteriaQuery;
    }

    /**
     * Returns available categories and it's count for applied product filters
     * @param map
     * @param criteriaBuilder
     * @param specification
     * @return
     */
    private List<CategoryDto> getCategoryFilters(Map<String, Object> map, CriteriaBuilder criteriaBuilder, Specification specification) {
        CriteriaQuery<Category> categoryCriteriaQuery =
                (CriteriaQuery<Category>) getCriteriaQuery(criteriaBuilder, specification, Category.class, Constants.CATEGORY);

        List<Category> categories = em.createQuery(categoryCriteriaQuery).getResultList();
        List<CategoryDto> categoryResponse = categories.stream()
                .map(category -> {
                    CategoryDto dto = modelMapper.map(category, CategoryDto.class);
                    Map<String, Object> requestMap = new HashMap(map);
                    requestMap.put(Constants.CATEGORY, dto.getName());
                    Specification specs = getProductSpecification(requestMap);
                    dto.setTotalCount(getProductCount(specs, Constants.CATEGORY));
                    return dto;
                })
                .collect(Collectors.toList());
        categoryResponse.sort(new Comparator<CategoryDto>() {
            @Override
            public int compare(CategoryDto c1, CategoryDto c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return categoryResponse;
    }

    /**
     * Returns available subcategories and it's count for applied product filters
     * @param map
     * @param criteriaBuilder
     * @param specification
     * @return
     */
    private List<SubCategoryDto> getSubCategoryFilters(Map<String, Object> map, CriteriaBuilder criteriaBuilder, Specification specification) {
        CriteriaQuery<SubCategory> subCategoryCriteriaQuery =
                (CriteriaQuery<SubCategory>) getCriteriaQuery(criteriaBuilder, specification, SubCategory.class, Constants.SUB_CATEGORY);
        List<SubCategory> subCategories = em.createQuery(subCategoryCriteriaQuery).getResultList();
        List<SubCategoryDto> subCategoryResponse = subCategories.stream()
                .map(subCategory -> {
                    SubCategoryDto dto = modelMapper.map(subCategory, SubCategoryDto.class);
                    Map<String, Object> requestMap = new HashMap(map);
                    requestMap.put(Constants.SUBCATEGORY, dto.getName());
                    Specification specs = getProductSpecification(requestMap);
                    dto.setTotalCount(getProductCount(specs, Constants.SUB_CATEGORY));
                    return dto;
                })
                .collect(Collectors.toList());
        subCategoryResponse.sort(new Comparator<SubCategoryDto>() {
            @Override
            public int compare(SubCategoryDto c1, SubCategoryDto c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return subCategoryResponse;
    }

    /**
     * Returns available colors for applied product filters
     * @param criteriaBuilder
     * @param specification
     * @return
     */
    private List<Color> getColorFilters(CriteriaBuilder criteriaBuilder, Specification specification) {
        CriteriaQuery<Color> colorCriteriaQuery =
                (CriteriaQuery<Color>) getCriteriaQuery(criteriaBuilder, specification, Color.class, Constants.COLOR);
        List<Color> colors = em.createQuery(colorCriteriaQuery).getResultList();
        return colors;
    }

    /**
     * Returns available brands and it's count for applied product filters
     * @param map
     * @param criteriaBuilder
     * @param specification
     * @return
     */
    private List<BrandDto> getBrandFilters(Map<String, Object> map, CriteriaBuilder criteriaBuilder, Specification specification) {
        CriteriaQuery<Brand> brandCriteriaQuery =
                (CriteriaQuery<Brand>) getCriteriaQuery(criteriaBuilder, specification, Brand.class, Constants.BRAND);
        List<Brand> brands = em.createQuery(brandCriteriaQuery).getResultList();
        List<BrandDto> brandsResponse = brands.stream()
                .map(brand -> {
                    BrandDto dto = modelMapper.map(brand, BrandDto.class);
                    Map<String, Object> requestMap = new HashMap(map);
                    requestMap.put(Constants.BRAND, Arrays.asList(dto.getName()));
                    Specification specs = getProductSpecification(requestMap);
                    dto.setTotalCount(getProductCount(specs, Constants.BRAND));
                    return dto;
                })
                .collect(Collectors.toList());
        brandsResponse.sort(new Comparator<BrandDto>() {
            @Override
            public int compare(BrandDto c1, BrandDto c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return brandsResponse;
    }

    /**
     * Returns available sizes for applied product filters along with count
     * @param map
     * @param criteriaBuilder
     * @param specification
     * @return
     */
    private List<SizeDto> getSizeFilters(Map<String, Object> map, CriteriaBuilder criteriaBuilder, Specification specification) {
        CriteriaQuery<Size> sizeCriteriaQuery =
                (CriteriaQuery<Size>) getCriteriaQuery(criteriaBuilder, specification, Size.class, Constants.SIZE);
        List<Size> sizes = em.createQuery(sizeCriteriaQuery).getResultList();
        List<SizeDto> sizesResponse = sizes.stream()
                .map(size -> {
                    SizeDto dto = modelMapper.map(size, SizeDto.class);
                    Map<String, Object> requestMap = new HashMap(map);
                    requestMap.put(Constants.SIZE, Arrays.asList(dto.getSize()));
                    Specification specs = getProductSpecification(requestMap);
                    dto.setTotalCount(getProductCount(specs, Constants.SIZE));
                    return dto;
                })
                .collect(Collectors.toList());
        return sizesResponse;
    }

    /**
     * Returns the count of products for categories, subcategories, brand and sizes.
     * @param specification
     * @param entityName
     * @return
     */
    private Integer getProductCount(Specification specification, String entityName) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Number> criteriaQuery = criteriaBuilder.createQuery(Number.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        criteriaQuery.select(criteriaBuilder.count(productRoot.get(entityName)));
        criteriaQuery.where(specification.toPredicate(productRoot, criteriaQuery, criteriaBuilder));
        Number returnNum = em.createQuery(criteriaQuery).getSingleResult();
        return returnNum != null ? returnNum.intValue(): 0;
    }
}
