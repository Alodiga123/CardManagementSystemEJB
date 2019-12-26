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
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.cms.commons.models.Product;
import com.cms.commons.models.ProductType;
import com.cms.commons.util.EjbConstants;
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
    
}
