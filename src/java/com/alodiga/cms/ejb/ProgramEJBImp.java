/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.AccessControlEJB;
import com.alodiga.cms.commons.ejb.AccessControlEJBLocal;
import com.alodiga.cms.commons.ejb.ProgramEJB;
import com.alodiga.cms.commons.ejb.ProgramEJBLocal;
import com.alodiga.cms.commons.exception.DisabledAccountException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.Account;
import com.cms.commons.util.EjbConstants;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

/**
 *
 * @author usuario
 */
@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.PROGRAM_EJB, mappedName = EjbConstants.PROGRAM_EJB)
@TransactionManagement(TransactionManagementType.BEAN)
public class ProgramEJBImp extends AbstractDistributionEJB implements ProgramEJB , ProgramEJBLocal {

    @Override
    public Account saveProgram(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException, DisabledAccountException {
      Account account = new Account();
      account.setLastName("");  
      return account;
    }

 
    
}
