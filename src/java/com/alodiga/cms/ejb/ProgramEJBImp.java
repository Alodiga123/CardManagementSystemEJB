/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.ProgramEJB;
import com.alodiga.cms.commons.ejb.ProgramEJBLocal;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.LegalPerson;
import com.cms.commons.models.NaturalPerson;
import com.cms.commons.models.Program;
import com.cms.commons.models.ProgramHasNetwork;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.Constants;
import com.cms.commons.util.QueryConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.Query;
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
        if (program == null) {
            throw new NullParameterException("program", null);
        }
        return (Program) saveEntity(program);
    }
    
    @Override
    public List<Program> getProgramByProgramType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Program> programList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        programList = (List<Program>) getNamedQueryResult(Program.class, QueryConstants.PROGRAM_BY_PROGRAM_TYPE, request, getMethodName(), logger, "programList");
        return programList;
    }
    
      
    //NaturalPerson
    @Override
    public List<NaturalPerson> getProgramOwner(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<NaturalPerson> programOwnerList = new ArrayList<NaturalPerson>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT n FROM NaturalPerson n, Person p WHERE n.personId.id = p.id AND p.personClassificationId.id = ?1");
        Query query = null;
        int idClassificationPerson = Constants.CLASSIFICATION_PERSON_PROGRAM_OWNER;
        try {
            query = createQuery(sqlBuilder.toString());
            query.setParameter("1",idClassificationPerson);
            programOwnerList = (List<NaturalPerson>) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            e.printStackTrace();            
        }
        return programOwnerList;
    }
    
    
    //LegalPerson
    @Override
    public List<LegalPerson> getCardManagementProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPerson> cardManagementProgramList = new ArrayList<LegalPerson>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT l FROM LegalPerson l, Person p WHERE l.personId.id = p.id AND p.personClassificationId.id = ?1");
        Query query = null;
        int idClassificationPerson = Constants.CLASSIFICATION_CARD_MANAGEMENT_PROGRAM;
        try {
            query = createQuery(sqlBuilder.toString());
            query.setParameter("1",idClassificationPerson);
            cardManagementProgramList = (List<LegalPerson>) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            e.printStackTrace();            
        }
        return cardManagementProgramList;
    }

    
    //ProgramHasNetwork
    @Override
    public List<ProgramHasNetwork> getProgramHasNetwork(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramHasNetwork> programHasNetwork = (List<ProgramHasNetwork>) listEntities(ProgramHasNetwork.class, request, logger, getMethodName());
        return programHasNetwork;
    }

    @Override
    public ProgramHasNetwork loadProgramHasNetwork(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProgramHasNetwork programHasNetwork = (ProgramHasNetwork) loadEntity(ProgramHasNetwork.class, request, logger, getMethodName());
        return programHasNetwork;
    }

    @Override
    public ProgramHasNetwork saveProgramHasNetwork(ProgramHasNetwork programHasNetwork) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (programHasNetwork == null) {
            throw new NullParameterException("programHasNetwork", null);
        }
        return (ProgramHasNetwork) saveEntity(programHasNetwork);
    }
    
    
}
