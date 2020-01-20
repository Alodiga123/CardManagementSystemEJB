package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.ProductEJB;
import com.alodiga.cms.commons.ejb.ProductEJBLocal;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.Channel;
import com.cms.commons.models.CommerceCategory;
import com.cms.commons.models.GeneralRate;
import com.cms.commons.models.LevelProduct;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.cms.commons.models.Product;
import com.cms.commons.models.ProductHasCommerceCategory;
import com.cms.commons.models.ProductType;
import com.cms.commons.models.ProductUse;
import com.cms.commons.models.RateApplicationType;
import com.cms.commons.models.SegmentCommerce;
import com.cms.commons.models.SegmentMarketing;
import com.cms.commons.models.StorageMedio;
import com.cms.commons.models.Transaction;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.List;

/**
 *
 * @author Yoan Leon
 * @since 20/12/2019
 */
@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.PRODUCT_EJB, mappedName = EjbConstants.PRODUCT_EJB)
@TransactionManagement(TransactionManagementType.BEAN)

public class ProductEJBImp extends AbstractDistributionEJB implements ProductEJBLocal, ProductEJB {
    
    private static final Logger logger = Logger.getLogger(UtilsEJBImp.class);
    
    @Override
    public List<Product> getProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Product> product = (List<Product>) listEntities(Product.class, request, logger, getMethodName());
        return product;
    }

    @Override
    public Product loadProduct(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Product product = (Product) loadEntity(Product.class, request, logger, getMethodName());
        return product;
    }

    @Override
    public Product saveProduct(Product product) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (product == null) {
            throw new NullParameterException("requestType", null);
        }
        return (Product) saveEntity(product);
    }
    
    //ProductType
    @Override
    public List<ProductType> getProductTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProductType> productTypes = (List<ProductType>) listEntities(ProductType.class, request, logger, getMethodName());
        return productTypes;
    }

    @Override
    public ProductType loadProductType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProductType productType = (ProductType) loadEntity(ProductType.class, request, logger, getMethodName());
        return productType;
    }

    @Override
    public ProductType saveProductType(ProductType productType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (productType == null) {
            throw new NullParameterException("productType", null);
        }
        return (ProductType) saveEntity(productType);
    }
    
    //LevelProduct
    @Override
    public List<LevelProduct> getLevelProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LevelProduct> levelProduct = (List<LevelProduct>) listEntities(LevelProduct.class, request, logger, getMethodName());
        return levelProduct;
    }

    @Override
    public LevelProduct loadLevelProduct(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LevelProduct levelProduct = (LevelProduct) loadEntity(LevelProduct.class, request, logger, getMethodName());
        return levelProduct;
    }

    @Override
    public LevelProduct saveLevelProduct(LevelProduct levelProduct) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (levelProduct == null) {
            throw new NullParameterException("levelProduct", null);
        }
        return (LevelProduct) saveEntity(levelProduct);
    }
    
    //ProductUse
    @Override
    public List<ProductUse> getProductUse(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProductUse> productUse = (List<ProductUse>) listEntities(ProductUse.class, request, logger, getMethodName());
        return productUse;
    }

    @Override
    public ProductUse loadProductUse(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProductUse productUse = (ProductUse) loadEntity(ProductUse.class, request, logger, getMethodName());
        return productUse;
    }

    @Override
    public ProductUse saveProductUse(ProductUse productUse) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (productUse == null) {
            throw new NullParameterException("productUse", null);
        }
        return (ProductUse) saveEntity(productUse);
    }
    
    //StorageMedio
    @Override
    public List<StorageMedio> getStorageMedio(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StorageMedio> storageMedio = (List<StorageMedio>) listEntities(StorageMedio.class, request, logger, getMethodName());
        return storageMedio;
    }

    @Override
    public StorageMedio loadStorageMedio(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StorageMedio storageMedio = (StorageMedio) loadEntity(StorageMedio.class, request, logger, getMethodName());
        return storageMedio;
    }

    @Override
    public StorageMedio saveStorageMedio(StorageMedio storageMedio) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (storageMedio == null) {
            throw new NullParameterException("storageMedio", null);
        }
        return (StorageMedio) saveEntity(storageMedio);
    }
    
    //SegmentMarketing
    @Override
    public List<SegmentMarketing> getSegmentMarketing(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<SegmentMarketing> segmentMarketing = (List<SegmentMarketing>) listEntities(SegmentMarketing.class, request, logger, getMethodName());
        return segmentMarketing;
    }

    @Override
    public SegmentMarketing loadSegmentMarketing(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SegmentMarketing segmentMarketing = (SegmentMarketing) loadEntity(SegmentMarketing.class, request, logger, getMethodName());
        return segmentMarketing;
    }

    @Override
    public SegmentMarketing saveSegmentMarketing(SegmentMarketing segmentMarketing) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (segmentMarketing == null) {
            throw new NullParameterException("segmentMarketing", null);
        }
        return (SegmentMarketing) saveEntity(segmentMarketing);
    }
    
    //SegmentCommerce
    @Override
    public List<SegmentCommerce> getSegmentCommerce(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<SegmentCommerce> segmentCommerce = (List<SegmentCommerce>) listEntities(SegmentCommerce.class, request, logger, getMethodName());
        return segmentCommerce;
    }

    @Override
    public SegmentCommerce loadSegmentCommerce(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SegmentCommerce segmentCommerce = (SegmentCommerce) loadEntity(SegmentCommerce.class, request, logger, getMethodName());
        return segmentCommerce;
    }

    @Override
    public SegmentCommerce saveSegmentCommerce(SegmentCommerce segmentCommerce) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (segmentCommerce == null) {
            throw new NullParameterException("segmentCommerce", null);
        }
        return (SegmentCommerce) saveEntity(segmentCommerce);
    }
    
    //CommerceCategory
    @Override
    public List<CommerceCategory> getCommerceCategory(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CommerceCategory> commerceCategory = (List<CommerceCategory>) listEntities(CommerceCategory.class, request, logger, getMethodName());
        return commerceCategory;
    }

    @Override
    public CommerceCategory loadCommerceCategory(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CommerceCategory commerceCategory = (CommerceCategory) loadEntity(CommerceCategory.class, request, logger, getMethodName());
        return commerceCategory;
    }

    @Override
    public CommerceCategory saveCommerceCategory(CommerceCategory commerceCategory) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (commerceCategory == null) {
            throw new NullParameterException("commerceCategory", null);
        }
        return (CommerceCategory) saveEntity(commerceCategory);
    }

    @Override
    public List<CommerceCategory> getCommerceCategoryBySegmentCommerce(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CommerceCategory> commerceCategoryList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_SEGMENT_COMMERCE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_SEGMENT_COMMERCE_ID), null);
        }
        commerceCategoryList = (List<CommerceCategory>) getNamedQueryResult(CommerceCategory.class, QueryConstants.COMMERCE_CATEGORY_BY_SEGMENT_COMMERCE, request, getMethodName(), logger, "commerceCategoryList");
        return commerceCategoryList;
    }
    
    //ProductHasCommerceCategory
    @Override
    public List<ProductHasCommerceCategory> getProductHasCommerceCategory(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProductHasCommerceCategory> productHasCommerceCategoryList = (List<ProductHasCommerceCategory>) listEntities(ProductHasCommerceCategory.class, request, logger, getMethodName());
        return productHasCommerceCategoryList;
    }

    @Override
    public ProductHasCommerceCategory loadProductHasCommerceCategory(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProductHasCommerceCategory productHasCommerceCategory = (ProductHasCommerceCategory) loadEntity(ProductHasCommerceCategory.class, request, logger, getMethodName());
        return productHasCommerceCategory;
    }

    @Override
    public ProductHasCommerceCategory saveProductHasCommerceCategory(ProductHasCommerceCategory productHasCommerceCategory) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (productHasCommerceCategory == null) {
            throw new NullParameterException("productHasCommerceCategory", null);
        }
        return (ProductHasCommerceCategory) saveEntity(productHasCommerceCategory);
    }

    @Override
    public List<ProductHasCommerceCategory> getCommerceCategoryByProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProductHasCommerceCategory> productHasCommerceCategoryList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_ID), null);
        }
        productHasCommerceCategoryList = (List<ProductHasCommerceCategory>) getNamedQueryResult(CommerceCategory.class, QueryConstants.COMMERCE_CATEGORY_BY_PRODUCT, request, getMethodName(), logger, "productHasCommerceCategoryList");
        return productHasCommerceCategoryList;
    }
    
    //Transaction 
    @Override
    public List<Transaction> getTransaction(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Transaction> transactionList = (List<Transaction>) listEntities(Transaction.class, request, logger, getMethodName());
        return transactionList;
    }

    @Override
    public Transaction loadTransaction(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Transaction transaction = (Transaction) loadEntity(Transaction.class, request, logger, getMethodName());
        return transaction;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (transaction == null) {
            throw new NullParameterException("transaction", null);
        }
        return (Transaction) saveEntity(transaction);
    }
    
    //GeneralRate
    @Override
    public List<GeneralRate> getGeneralRate(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<GeneralRate> generalRateList = (List<GeneralRate>) listEntities(GeneralRate.class, request, logger, getMethodName());
        return generalRateList;
    }

    @Override
    public GeneralRate loadGeneralRate(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        GeneralRate generalRate = (GeneralRate) loadEntity(GeneralRate.class, request, logger, getMethodName());
        return generalRate;
    }

    @Override
    public GeneralRate saveGeneralRate(GeneralRate generalRate) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (generalRate == null) {
            throw new NullParameterException("generalRate", null);
        }
        return (GeneralRate) saveEntity(generalRate);
    }

    @Override
    public List<Channel> getChannel(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Channel loadChannel(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Channel saveChannel(Channel channel) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RateApplicationType> getRateApplicationType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RateApplicationType loadRateApplicationType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RateApplicationType saveRateApplicationType(RateApplicationType rateApplicationType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
