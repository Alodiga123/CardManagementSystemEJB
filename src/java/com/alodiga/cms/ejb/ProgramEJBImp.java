/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.ProgramEJB;
import com.alodiga.cms.commons.ejb.ProgramEJBLocal;
import com.alodiga.cms.commons.exception.DisabledAccountException;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.Account;
import com.cms.commons.models.Program;
import com.cms.commons.models.RequestType;
import com.cms.commons.util.EjbConstants;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;

/**
 *
 * @author usuario
 */
@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.PROGRAM_EJB, mappedName = EjbConstants.PROGRAM_EJB)
@TransactionManagement(TransactionManagementType.BEAN)



public class ProgramEJBImp extends AbstractDistributionEJB implements ProgramEJB , ProgramEJBLocal {    
    private static final Logger logger = Logger.getLogger(ProgramEJBImp.class);
    
    
    @Override
    public List<Program> getProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 //Program
    @Override
    public List<Program> getProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Program> program = (List<Program>) listEntities(Program.class, request, logger, getMethodName());
        return program;
    }
    @Override
    public Program loadProgram(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Program program = (Program) loadEntity(Program.class, request, logger, getMethodName());
        return program;
    }
    @Override
    public Program saveProgram(Program program) throws RegisterNotFoundException, NullParameterException, GeneralException {
        return (Program) saveEntity(program);
    }

    
}
