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
import com.cms.commons.models.ApprovalGeneralRate;
import com.cms.commons.models.ApprovalProductRate;
import com.cms.commons.models.ApprovalProgramRate;
import com.cms.commons.models.Channel;
import com.cms.commons.models.CommerceCategory;
import com.cms.commons.models.Country;
import com.cms.commons.models.GeneralRate;
import com.cms.commons.models.LevelProduct;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.cms.commons.models.Product;
import com.cms.commons.models.ProductHasChannelHasTransaction;
import com.cms.commons.models.ProductHasCommerceCategory;
import com.cms.commons.models.ProductType;
import com.cms.commons.models.ProductUse;
import com.cms.commons.models.Program;
import com.cms.commons.models.RateApplicationType;
import com.cms.commons.models.RateByProduct;
import com.cms.commons.models.RateByProgram;
import com.cms.commons.models.SegmentCommerce;
import com.cms.commons.models.SegmentMarketing;
import com.cms.commons.models.StatusProduct;
import com.cms.commons.models.StorageMedio;
import com.cms.commons.models.Transaction;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
    public List<Product> getProductByProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Product> productByProgram = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        productByProgram = (List<Product>) getNamedQueryResult(Product.class, QueryConstants.PRODUCT_BY_PROGRAM, request, getMethodName(), logger, "productByProgram");
        return productByProgram;
    }

    @Override
    public Product loadProduct(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Product product = (Product) loadEntity(Product.class, request, logger, getMethodName());
        return product;
    }

    @Override
    public Product saveProduct(Product product) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (product == null) {
            throw new NullParameterException("product", null);
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

    //CommerceCapublic ProductHasCommerceCategory getProductHasCommerceCategoryBD(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException;tegory
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
        GeneralRate general = null;
        if (generalRate == null) {
            throw new NullParameterException("generalRate", null);
        }
        try {
            general = (GeneralRate) saveEntity(generalRate);
        } catch (Exception e) {
            e.getMessage();
        }
        return general;
    }
    @Override
    public List<RateApplicationType> getRateApplicationType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RateApplicationType> rateApplicationTypeList = (List<RateApplicationType>) listEntities(RateApplicationType.class, request, logger, getMethodName());
        return rateApplicationTypeList;
    }

    @Override
    public RateApplicationType loadRateApplicationType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RateApplicationType saveRateApplicationType(RateApplicationType rateApplicationType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }
        

    //ProductHasChannelHasTransaction
    @Override
    public List<ProductHasChannelHasTransaction> getProductHasChannelHasTransaction(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProductHasChannelHasTransaction> productHasChannelHasTransaction = (List<ProductHasChannelHasTransaction>) listEntities(ProductHasChannelHasTransaction.class, request, logger, getMethodName());
        return productHasChannelHasTransaction;

    }

    @Override
    public List<ProductHasChannelHasTransaction> getProductHasChannelHasTransactionByProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProductHasChannelHasTransaction> productHasChannelHasTransaction = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_ID), null);
        }
        productHasChannelHasTransaction = (List<ProductHasChannelHasTransaction>) getNamedQueryResult(CommerceCategory.class, QueryConstants.PRODUCT_HAS_CHANNEL_HAS_TRANSACTION_BY_PRODUCT, request, getMethodName(), logger, "productHasChannelHasTransaction");
        return productHasChannelHasTransaction;
    }

    @Override
    public ProductHasChannelHasTransaction loadProductHasChannelHasTransaction(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProductHasChannelHasTransaction productHasChannelHasTransaction = (ProductHasChannelHasTransaction) loadEntity(ProductHasChannelHasTransaction.class, request, logger, getMethodName());
        return productHasChannelHasTransaction;
    }

    @Override
    public ProductHasChannelHasTransaction saveProductHasChannelHasTransaction(ProductHasChannelHasTransaction productHasChannelHasTransaction) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (productHasChannelHasTransaction == null) {
            throw new NullParameterException("productHasChannelHasTransaction", null);
        }
        return (ProductHasChannelHasTransaction) saveEntity(productHasChannelHasTransaction);
    }

    //Transaction
    @Override
    public List<Transaction> getTransaction(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Transaction> transaction = (List<Transaction>) listEntities(Transaction.class, request, logger, getMethodName());
        return transaction;
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

    //Channel
    @Override
    public List<Channel> getChannel(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Channel> channel = (List<Channel>) listEntities(Channel.class, request, logger, getMethodName());
        return channel;
    }

    @Override
    public Channel loadChannel(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Channel channel = (Channel) loadEntity(Channel.class, request, logger, getMethodName());
        return channel;
    }

    @Override
    public Channel saveChannel(Channel channel) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (channel == null) {
            throw new NullParameterException("channel", null);
        }
        return (Channel) saveEntity(channel);
    }
    
    //GeneralRateByProductType
    @Override
    public List<GeneralRate> getGeneralRateByProductType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<GeneralRate> generalRateList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_TYPE_ID), null);
        }
        generalRateList = (List<GeneralRate>) getNamedQueryResult(GeneralRate.class, QueryConstants.GENERAL_RATE_BY_PRODUCT_TYPE, request, getMethodName(), logger, "generalRateList");
        return generalRateList;
    }
    
    //RateByProgram
    @Override
    public List<RateByProgram> getRateByProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RateByProgram> rateByProgramList = (List<RateByProgram>) listEntities(RateByProgram.class, request, logger, getMethodName());
        return rateByProgramList;
    }

    @Override
    public RateByProgram loadRateByProgram(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        RateByProgram rateByProgram = (RateByProgram) loadEntity(RateByProgram.class, request, logger, getMethodName());
        return rateByProgram;
    }

    @Override
    public RateByProgram saveRateByProgram(RateByProgram rateByProgram) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (rateByProgram == null) {
            throw new NullParameterException("rateByProgram", null);
        }
        return (RateByProgram) saveEntity(rateByProgram);
    }

    @Override
    public List<RateByProgram> getRateByProgramByTransactionByChannel(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RateByProgram> rateByProgramList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_TRANSACTION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_TRANSACTION_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_CHANNEL_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CHANNEL_ID), null);
        }
        rateByProgramList = (List<RateByProgram>) getNamedQueryResult(RateByProgram.class, QueryConstants.RATE_BY_PROGRAM_BY_TRANSACTIONS_BY_CHANNEL, request, getMethodName(), logger, "rateByProgramList");
        return rateByProgramList;
    }

    @Override
    public List<RateByProgram> getRateByProgramByProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RateByProgram> rateByProgramList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        rateByProgramList = (List<RateByProgram>) getNamedQueryResult(RateByProgram.class, QueryConstants.RATE_BY_PROGRAM_BY_PROGRAM, request, getMethodName(), logger, "rateByProgramList");
        return rateByProgramList;
    }
    
    //RateByProduct
    @Override
    public List<RateByProduct> getRateByProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RateByProduct> rateByProductList = (List<RateByProduct>) listEntities(RateByProduct.class, request, logger, getMethodName());
        return rateByProductList;
    }

    @Override
    public RateByProduct loadRateByProduct(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        RateByProduct rateByProduct = (RateByProduct) loadEntity(RateByProduct.class, request, logger, getMethodName());
        return rateByProduct;
    }

    @Override
    public RateByProduct saveRateByProduct(RateByProduct rateByProduct) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (rateByProduct == null) {
            throw new NullParameterException("rateByProduct", null);
        }
        return (RateByProduct) saveEntity(rateByProduct);
    }

    @Override
    public List<RateByProduct> getRateByProductByProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<RateByProduct> rateByProductList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_ID), null);
        }
        rateByProductList = (List<RateByProduct>) getNamedQueryResult(RateByProduct.class, QueryConstants.RATE_BY_PRODUCT_BY_PRODUCT, request, getMethodName(), logger, "rateByProductList");
        return rateByProductList;
    }

    @Override
    public List<ProductHasCommerceCategory> getProductHasCommerceCategoryBD(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProductHasCommerceCategory> productHasCommerceCategoryList = null;
        Map<String, Object> params = request.getParams(); 
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_COMMERCE_CATEGORY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COMMERCE_CATEGORY_ID), null);
        }
        productHasCommerceCategoryList = (List<ProductHasCommerceCategory>) getNamedQueryResult(ProductHasCommerceCategory.class, QueryConstants.COMMERCE_CATEGORY_FIND_BD, request, getMethodName(), logger, "productHasCommerceCategoryList");
        return productHasCommerceCategoryList;
    }

    @Override
    public List<ApprovalGeneralRate> getApprovalGeneralRate(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApprovalGeneralRate> approvalGeneralRateList = (List<ApprovalGeneralRate>) listEntities(ApprovalGeneralRate.class, request, logger, getMethodName());
        return approvalGeneralRateList;
    }

    @Override
    public ApprovalGeneralRate loadApprovalGeneralRate(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ApprovalGeneralRate approvalGeneralRate = (ApprovalGeneralRate) loadEntity(ApprovalGeneralRate.class, request, logger, getMethodName());
        return approvalGeneralRate;
    }

    @Override
    public ApprovalGeneralRate saveApprovalGeneralRate(ApprovalGeneralRate approvalGeneralRate) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (approvalGeneralRate == null) {
            throw new NullParameterException("approvalGeneralRate", null);
        }
        return (ApprovalGeneralRate) saveEntity(approvalGeneralRate);
    }

    @Override
    public List<ApprovalProgramRate> getApprovalProgramRate(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApprovalProgramRate> approvalProgramRateList = (List<ApprovalProgramRate>) listEntities(ApprovalProgramRate.class, request, logger, getMethodName());
        return approvalProgramRateList;
    }

    @Override
    public ApprovalProgramRate loadApprovalProgramRate(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ApprovalProgramRate approvalProgramRate = (ApprovalProgramRate) loadEntity(ApprovalProgramRate.class, request, logger, getMethodName());
        return approvalProgramRate;
    }

    @Override
    public ApprovalProgramRate saveApprovalProgramRate(ApprovalProgramRate approvalProgramRate) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (approvalProgramRate == null) {
            throw new NullParameterException("approvalProgramRate", null);
        }
        return (ApprovalProgramRate) saveEntity(approvalProgramRate);
    }   

    @Override
    public List<StatusProduct> getStatusProduct(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusProduct> statusProductList = (List<StatusProduct>) listEntities(StatusProduct.class, request, logger, getMethodName());
        return statusProductList;
    }

    @Override
    public StatusProduct loadStatusProduct(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusProduct statusProduct = (StatusProduct) loadEntity(StatusProduct.class, request, logger, getMethodName());
        return statusProduct;
    }

    @Override
    public StatusProduct saveStatusProduct(StatusProduct statusProduct) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (statusProduct == null) {
            throw new NullParameterException("statusProduct", null);
        }
        return (StatusProduct) saveEntity(statusProduct);
    }

    @Override
    public List<ApprovalProductRate> getApprovalProductRate(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApprovalProductRate> approvalProductRateList = (List<ApprovalProductRate>) listEntities(ApprovalProductRate.class, request, logger, getMethodName());
        return approvalProductRateList;
    }

    @Override
    public ApprovalProductRate loadApprovalProductRate(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ApprovalProductRate approvalProductRate = (ApprovalProductRate) loadEntity(ApprovalProductRate.class, request, logger, getMethodName());
        return approvalProductRate;
    }

    @Override
    public ApprovalProductRate saveApprovalProductRate(ApprovalProductRate approvalProductRate) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (approvalProductRate == null) {
            throw new NullParameterException("approvalProductRate", null);
        }
        return (ApprovalProductRate) saveEntity(approvalProductRate);
    }

    @Override
    public List<Product> searchProduct(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<Product> productList= null; 
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }      //To change body of generated methods, choose Tools | Templates.
            
            StringBuilder sqlBuilder = new StringBuilder("select * from product p where p.name like '%");
            sqlBuilder.append(name);
            sqlBuilder.append("%'");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Product.class);
            productList = (List<Product>) query.setHint("toplink.refresh", "true").getResultList();
            
                } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return productList;
    }

    @Override
    public List<GeneralRate> getGeneralRateBy4field(GeneralRate generalRate) throws EmptyListException, GeneralException, NullParameterException {
        List<GeneralRate> generalRateList= null; 
        try {
            if (generalRate == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "generalRate"), null);
            }      //To change body of generated methods, choose Tools | Templates.
            
            StringBuilder sqlBuilder = new StringBuilder("select * from generalRate where countryId=");
            sqlBuilder.append(generalRate.getCountryId().getId());
            sqlBuilder.append(" and transactionId=");
            sqlBuilder.append(generalRate.getTransactionId().getId());
            sqlBuilder.append(" and productTypeId=");
            sqlBuilder.append(generalRate.getProductTypeId().getId());
            sqlBuilder.append(" and channelId=");
            sqlBuilder.append(generalRate.getChannelId().getId());
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), GeneralRate.class);
            generalRateList = (List<GeneralRate>) query.setHint("toplink.refresh", "true").getResultList();
            
                } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return generalRateList;

    }

    @Override
    public List<GeneralRate> getGeneralRateByCountry(Country country) throws EmptyListException, GeneralException, NullParameterException {
    List<GeneralRate> generalRateList= null; 
        try {
            if (country == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "country"), null);
            }      //To change body of generated methods, choose Tools | Templates.
            
            StringBuilder sqlBuilder = new StringBuilder("select * from generalRate where countryId=");
            sqlBuilder.append(country.getId());
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), GeneralRate.class);
            generalRateList = (List<GeneralRate>) query.setHint("toplink.refresh", "true").getResultList();
            
                } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return generalRateList;

    }

            
        }
