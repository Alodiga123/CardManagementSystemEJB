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
import com.cms.commons.models.DaysWeek;
import com.cms.commons.models.DaysWeekHasProgramLoyalty;
import com.cms.commons.models.LegalPerson;
import com.cms.commons.models.LoyaltyTransactionHasCommerceCategory;
import com.cms.commons.models.NaturalPerson;
import com.cms.commons.models.Program;
import com.cms.commons.models.ProgramHasNetwork;
import com.cms.commons.models.ProgramLoyalty;
import com.cms.commons.models.ProgramLoyaltyTransaction;
import com.cms.commons.models.ProgramLoyaltyType;
import com.cms.commons.models.ProjectAnnualVolume;
import com.cms.commons.models.StatusProgramLoyalty;
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

public class ProgramEJBImp extends AbstractDistributionEJB implements ProgramEJB, ProgramEJBLocal {

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
    
    @Override
    public List<Program> getProgramByProductType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Program> programList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PRODUCT_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PRODUCT_TYPE_ID), null);
        }
        programList = (List<Program>) getNamedQueryResult(Program.class, QueryConstants.PROGRAM_BY_PRODUCT_TYPE, request, getMethodName(), logger, "programList");
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
            query.setParameter("1", idClassificationPerson);
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
            query.setParameter("1", idClassificationPerson);
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
    public List<ProgramHasNetwork> getProgramHasNetworkByNetworkByProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramHasNetwork> programHasNetworkList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_NETWORK_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_NETWORK_ID), null);
        }
        programHasNetworkList = (List<ProgramHasNetwork>) getNamedQueryResult(ProgramHasNetwork.class, QueryConstants.PROGRAM_HAS_NETWORK_BY_NETWORK_BY_PROGRAM, request, getMethodName(), logger, "programHasNetworkList");
        return programHasNetworkList;
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

    //ProgramLoyalty
    @Override
    public List<ProgramLoyalty> getProgramLoyalty(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramLoyalty> programLoyalty = (List<ProgramLoyalty>) listEntities(ProgramLoyalty.class, request, logger, getMethodName());
        return programLoyalty;
    }

    @Override
    public ProgramLoyalty loadProgramLoyalty(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProgramLoyalty programLoyalty = (ProgramLoyalty) loadEntity(ProgramLoyalty.class, request, logger, getMethodName());
        return programLoyalty;
    }

    @Override
    public ProgramLoyalty saveProgramLoyalty(ProgramLoyalty programLoyalty) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (programLoyalty == null) {
            throw new NullParameterException("programLoyalty", null);
        }
        return (ProgramLoyalty) saveEntity(programLoyalty);
    }

    //ProgramLoyaltyType
    @Override
    public List<ProgramLoyaltyType> getProgramLoyaltyType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramLoyaltyType> programLoyaltyTypes = (List<ProgramLoyaltyType>) listEntities(ProgramLoyaltyType.class, request, logger, getMethodName());
        return programLoyaltyTypes;
    }

    @Override
    public ProgramLoyaltyType loadProgramLoyaltyType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProgramLoyaltyType programLoyaltyType = (ProgramLoyaltyType) loadEntity(ProgramLoyaltyType.class, request, logger, getMethodName());
        return programLoyaltyType;
    }

    @Override
    public ProgramLoyaltyType saveProgramLoyaltyType(ProgramLoyaltyType programLoyaltyType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (programLoyaltyType == null) {
            throw new NullParameterException("programLoyaltyType", null);
        }
        return (ProgramLoyaltyType) saveEntity(programLoyaltyType);
    }

    //
    @Override
    public List<StatusProgramLoyalty> getStatusProgramLoyalty(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusProgramLoyalty> statusProgramLoyalty = (List<StatusProgramLoyalty>) listEntities(StatusProgramLoyalty.class, request, logger, getMethodName());
        return statusProgramLoyalty;
    }

    @Override
    public StatusProgramLoyalty loadStatusProgramLoyalty(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusProgramLoyalty statusProgramLoyalty = (StatusProgramLoyalty) loadEntity(StatusProgramLoyalty.class, request, logger, getMethodName());
        return statusProgramLoyalty;
    }

    @Override
    public StatusProgramLoyalty saveStatusProgramLoyalty(StatusProgramLoyalty statusProgramLoyalty) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (statusProgramLoyalty == null) {
            throw new NullParameterException("statusProgramLoyalty", null);
        }
        return (StatusProgramLoyalty) saveEntity(statusProgramLoyalty);
    }

    //DaysWeekHasProgramLoyalty
    @Override
    public List<DaysWeekHasProgramLoyalty> getDaysWeekHasProgramLoyalty(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DaysWeekHasProgramLoyalty> daysWeekHasProgramLoyalty = (List<DaysWeekHasProgramLoyalty>) listEntities(DaysWeekHasProgramLoyalty.class, request, logger, getMethodName());
        return daysWeekHasProgramLoyalty;
    }

    @Override
    public List<DaysWeekHasProgramLoyalty> getDaysWeekHasProgramLoyaltyByLoyalty(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DaysWeekHasProgramLoyalty> daysWeekHasProgramLoyaltyByLoyalty = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_LOYALTY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_LOYALTY_ID), null);
        }
        daysWeekHasProgramLoyaltyByLoyalty = (List<DaysWeekHasProgramLoyalty>) getNamedQueryResult(DaysWeekHasProgramLoyalty.class, QueryConstants.DAYS_WEEK_HAS_PROGRAM_BY_LOYALTY, request, getMethodName(), logger, "daysWeekHasProgramLoyaltyByLoyalty");
        return daysWeekHasProgramLoyaltyByLoyalty;
    }

    @Override
    public DaysWeekHasProgramLoyalty loadDaysWeekHasProgramLoyalty(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        DaysWeekHasProgramLoyalty daysWeekHasProgramLoyalty = (DaysWeekHasProgramLoyalty) loadEntity(DaysWeekHasProgramLoyalty.class, request, logger, getMethodName());
        return daysWeekHasProgramLoyalty;
    }

    @Override
    public DaysWeekHasProgramLoyalty saveDaysWeekHasProgramLoyalty(DaysWeekHasProgramLoyalty daysWeekHasProgramLoyalty) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (daysWeekHasProgramLoyalty == null) {
            throw new NullParameterException("daysWeekHasProgramLoyalty", null);
        }
        return (DaysWeekHasProgramLoyalty) saveEntity(daysWeekHasProgramLoyalty);
    }

    //DaysWeek
    @Override
    public List<DaysWeek> getDaysWeek(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DaysWeek> daysWeek = (List<DaysWeek>) listEntities(DaysWeek.class, request, logger, getMethodName());
        return daysWeek;
    }

    @Override
    public DaysWeek loadDaysWeek(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        DaysWeek daysWeek = (DaysWeek) loadEntity(DaysWeek.class, request, logger, getMethodName());
        return daysWeek;
    }

    @Override
    public DaysWeek saveDaysWeek(DaysWeek daysWeek) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (daysWeek == null) {
            throw new NullParameterException("daysWeek", null);
        }
        return (DaysWeek) saveEntity(daysWeek);
    }

    @Override
    public List<ProgramLoyaltyTransaction> getProgramLoyaltyTransaction(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramLoyaltyTransaction> programLoyaltyTransaction = (List<ProgramLoyaltyTransaction>) listEntities(ProgramLoyaltyTransaction.class, request, logger, getMethodName());
        return programLoyaltyTransaction;
    }

    @Override
    public List<ProgramLoyaltyTransaction> getProgramLoyaltyTransactionByLoyalty(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramLoyaltyTransaction> ProgramLoyaltyTransactionByLoyalty = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_LOYALTY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_LOYALTY_ID), null);
        }
        ProgramLoyaltyTransactionByLoyalty = (List<ProgramLoyaltyTransaction>) getNamedQueryResult(ProgramLoyaltyTransaction.class, QueryConstants.PROGRAM_LOYALTY_TRANSACTION_BY_LOYALTY, request, getMethodName(), logger, "ProgramLoyaltyTransactionByLoyalty");
        return ProgramLoyaltyTransactionByLoyalty;
    }

    @Override
    public List<ProgramLoyaltyTransaction> getProgramLoyaltyTransactionUnique(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProgramLoyaltyTransaction> programLoyaltyTransactionUnique = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CHANNEL_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CHANNEL_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_LOYALTY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_LOYALTY_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_TRANSACTION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_TRANSACTION_ID), null);
        }

        programLoyaltyTransactionUnique = (List<ProgramLoyaltyTransaction>) getNamedQueryResult(ProgramLoyaltyTransaction.class, QueryConstants.PROGRAM_LOYALTY_TRANSACTION_UNIQUE, request, getMethodName(), logger, "programLoyaltyTransactionUnique");
        return programLoyaltyTransactionUnique;
    }

    @Override
    public ProgramLoyaltyTransaction loadProgramLoyaltyTransaction(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProgramLoyaltyTransaction programLoyaltyTransaction = (ProgramLoyaltyTransaction) loadEntity(ProgramLoyaltyTransaction.class, request, logger, getMethodName());
        return programLoyaltyTransaction;
    }

    @Override
    public ProgramLoyaltyTransaction saveProgramLoyaltyTransaction(ProgramLoyaltyTransaction programLoyaltyTransaction) throws RegisterNotFoundException, NullParameterException, GeneralException {
        //public ProgramLoyaltyTransaction saveProgramLoyaltyTransaction(ProgramLoyaltyTransaction programLoyaltyTransaction) throws RegisterNotFoundException, NullParameterException, GeneralException, RegisterDuplicateException {    
        //TODO LOAD programLoyaltyTransaction con el método loadProgramLoyaltyTransaction y si te devuelve la info 

        if (programLoyaltyTransaction == null) {
            throw new NullParameterException("programLoyaltyTransaction", null);
        }
        return (ProgramLoyaltyTransaction) saveEntity(programLoyaltyTransaction);
    }

    //LoyaltyTransactionHasCommerceCategory
    @Override
    public List<LoyaltyTransactionHasCommerceCategory> getLoyaltyTransactionHasCommerceCategory(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LoyaltyTransactionHasCommerceCategory> loyaltyTransactionHasCommerceCategory = (List<LoyaltyTransactionHasCommerceCategory>) listEntities(LoyaltyTransactionHasCommerceCategory.class, request, logger, getMethodName());
        return loyaltyTransactionHasCommerceCategory;
    }

    @Override
    public List<LoyaltyTransactionHasCommerceCategory> getLoyaltyTransactionHasCommerceCategoryByTransaction(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LoyaltyTransactionHasCommerceCategory> loyaltyTransactionHasCommerceCategoryByTransaction = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_LOYALTY_TRANSACTION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_LOYALTY_TRANSACTION_ID), null);
        }
        loyaltyTransactionHasCommerceCategoryByTransaction = (List<LoyaltyTransactionHasCommerceCategory>) getNamedQueryResult(LoyaltyTransactionHasCommerceCategory.class, QueryConstants.LOYALTY_TRANSACTION_COMMERCE_BY_TRANSACTION, request, getMethodName(), logger, "loyaltyTransactionHasCommerceCategoryByTransaction");
        return loyaltyTransactionHasCommerceCategoryByTransaction;
    }

    @Override
    public List<LoyaltyTransactionHasCommerceCategory> getLoyaltyTransactionHasCommerceCategoryUnique(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LoyaltyTransactionHasCommerceCategory> loyaltyTransactionHasCommerceCategoryUnique = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COMMERCE_CATEGORY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COMMERCE_CATEGORY_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_LOYALTY_TRANSACTION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_LOYALTY_TRANSACTION_ID), null);
        }

        loyaltyTransactionHasCommerceCategoryUnique = (List<LoyaltyTransactionHasCommerceCategory>) getNamedQueryResult(ProgramLoyaltyTransaction.class, QueryConstants.LOYALTY_TRANSACTION_COMMERCE_UNIQUE, request, getMethodName(), logger, "loyaltyTransactionHasCommerceCategoryUnique");
        return loyaltyTransactionHasCommerceCategoryUnique;
    }
    
    @Override
    public LoyaltyTransactionHasCommerceCategory loadLoyaltyTransactionHasCommerceCategory(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LoyaltyTransactionHasCommerceCategory loyaltyTransactionHasCommerceCategory = (LoyaltyTransactionHasCommerceCategory) loadEntity(LoyaltyTransactionHasCommerceCategory.class, request, logger, getMethodName());
        return loyaltyTransactionHasCommerceCategory;
    }

    @Override
    public LoyaltyTransactionHasCommerceCategory saveLoyaltyTransactionHasCommerceCategory(LoyaltyTransactionHasCommerceCategory loyaltyTransactionHasCommerceCategory) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (loyaltyTransactionHasCommerceCategory == null) {
            throw new NullParameterException("loyaltyTransactionHasCommerceCategory", null);
        }
        return (LoyaltyTransactionHasCommerceCategory) saveEntity(loyaltyTransactionHasCommerceCategory);
    }
    
    //ProjectAnnualVolume
    @Override
    public List<ProjectAnnualVolume> getProjectAnnualVolume(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
       List<ProjectAnnualVolume> projectAnnualVolume = (List<ProjectAnnualVolume>) listEntities(ProjectAnnualVolume.class, request, logger, getMethodName());
        return projectAnnualVolume;
    }

    @Override
    public ProjectAnnualVolume loadProjectAnnualVolume(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProjectAnnualVolume projectAnnualVolume = (ProjectAnnualVolume) loadEntity(ProjectAnnualVolume.class, request, logger, getMethodName());
        return projectAnnualVolume;
    }

    @Override
    public ProjectAnnualVolume saveProjectAnnualVolume(ProjectAnnualVolume projectAnnualVolume) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (projectAnnualVolume == null) {
            throw new NullParameterException("projectAnnualVolume", null);
        }
        return (ProjectAnnualVolume) saveEntity(projectAnnualVolume);
    }

    @Override
    public List<ProjectAnnualVolume> getProjectAnnualVolumeByProgram(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ProjectAnnualVolume> projectAnnualVolumeByProgram = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PROGRAM_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PROGRAM_ID), null);
        }
        projectAnnualVolumeByProgram = (List<ProjectAnnualVolume>) getNamedQueryResult(ProjectAnnualVolume.class, QueryConstants.PROJECT_ANNUAL_VOLUME_BY_PROGRAM, request, getMethodName(), logger, "projectAnnualVolumeByProgram");
        return projectAnnualVolumeByProgram;
   
    }
   
}
