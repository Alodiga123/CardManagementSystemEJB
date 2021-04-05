//PersonEJB
package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.PersonEJB;
import com.alodiga.cms.commons.ejb.PersonEJBLocal;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.InvalidQuestionException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.enumeraciones.StatusApplicantE;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.AdditionalInformationNaturalCustomer;
import com.cms.commons.models.ApplicantNaturalPerson;
import com.cms.commons.models.CardRequestNaturalPerson;
import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.ComercialAgency;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.Employee;
import com.cms.commons.models.EmployedPosition;
import com.cms.commons.models.FamilyReferences;
import com.cms.commons.models.Issuer;
import com.cms.commons.models.IssuerType;
import com.cms.commons.models.KinShipApplicant;
import com.cms.commons.models.LegalCustomer;
import com.cms.commons.models.LegalCustomerHasLegalRepresentatives;
import com.cms.commons.models.LegalPerson;
import com.cms.commons.models.LegalPersonHasLegalRepresentatives;
import com.cms.commons.models.NaturalCustomer;
import com.cms.commons.models.NaturalPerson;
import com.cms.commons.models.PasswordChangeRequest;
import com.cms.commons.models.Person;
import com.cms.commons.models.PersonHasAddress;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.PhonePerson;
import com.cms.commons.models.PhoneType;
import com.cms.commons.models.PlasticManufacturer;
import com.cms.commons.models.Product;
import com.cms.commons.models.Profession;
import com.cms.commons.models.StatusCustomer;
import com.cms.commons.models.User;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author jose
 */
@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.PERSON_EJB, mappedName = EjbConstants.PERSON_EJB)
@TransactionManagement(TransactionManagementType.BEAN)

public class PersonEJBImp extends AbstractDistributionEJB implements PersonEJB, PersonEJBLocal {

    private static final Logger logger = Logger.getLogger(ProgramEJBImp.class);

    //PhonePerson
    @Override
    public List<PhonePerson> getPhonePerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PhonePerson> phonePerson = (List<PhonePerson>) listEntities(PhonePerson.class, request, logger, getMethodName());
        return phonePerson;
    }

    @Override
    public PhonePerson loadPhonePerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PhonePerson phonePerson = (PhonePerson) loadEntity(PhonePerson.class, request, logger, getMethodName());
        return phonePerson;
    }

    @Override
    public PhonePerson savePhonePerson(PhonePerson phonePerson) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (phonePerson == null) {
            throw new NullParameterException("phonePerson", null);
        }
        return (PhonePerson) saveEntity(phonePerson);
    }

    @Override
    public List<PhonePerson> getPhoneByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PhonePerson> phonePersonList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        phonePersonList = (List<PhonePerson>) getNamedQueryResult(PhonePerson.class, QueryConstants.PHONES_BY_PERSON, request, getMethodName(), logger, "phonePersonList");
        return phonePersonList;
    }
    
    @Override
    public Long havePhonesByPerson(Long personId) throws GeneralException, NullParameterException {
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(p.id) FROM phonePerson p WHERE p.personId = ?1");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", personId);
        List result = (List) query.setHint("toplink.refresh", "true").getResultList();
        return result.get(0) != null ? (Long) result.get(0) : 0l;
    }
    
    public PhonePerson validatePhoneQuestion(Long personId, String numberPhone) throws RegisterNotFoundException, NullParameterException, GeneralException, InvalidQuestionException{
        try {
            //Se obtiene el cliente
            Query query = entityManager.createQuery("SELECT p FROM PhonePerson p WHERE p.personId.id = :personId");
            query.setParameter("personId", personId);
            PhonePerson phonePerson = (PhonePerson) query.getSingleResult();
            
            //Se valida las respuestas del Telefono en BD
            if (!phonePerson.getNumberPhone().equals(numberPhone)) {
                throw new InvalidQuestionException(com.cms.commons.util.Constants.INVALID_QUESTION_EXCEPTION);
            }
            return phonePerson;
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
    }
    
     public List<PhonePerson> getValidateMainPhone(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PhonePerson> phonePersonList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }       
        phonePersonList = (List<PhonePerson>) getNamedQueryResult(PhonePerson.class, QueryConstants.PHONES_BY_MAIN , request, getMethodName(), logger, "phonePersonList");
        return phonePersonList;
    }  
    
    //PersonHasAddress
    @Override
    public List<PersonHasAddress> getPersonHasAddresses(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonHasAddress> personHasAddress = (List<PersonHasAddress>) listEntities(PersonHasAddress.class, request, logger, getMethodName());
        return personHasAddress;
    }

    @Override
    public List<PersonHasAddress> getPersonHasAddressesByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonHasAddress> personHasAddressByPerson = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        personHasAddressByPerson = (List<PersonHasAddress>) getNamedQueryResult(UtilsEJB.class, QueryConstants.PERSON_HAS_ADDRESS_BY_PERSON, request, getMethodName(), logger, "personHasAddressByPerson");
        return personHasAddressByPerson;
    }

    @Override
    public PersonHasAddress loadPersonHasAddress(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PersonHasAddress personHasAddress = (PersonHasAddress) loadEntity(PersonHasAddress.class, request, logger, getMethodName());
        return personHasAddress;
    }

    @Override
    public PersonHasAddress savePersonHasAddress(PersonHasAddress personHasAddress) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (personHasAddress == null) {
            throw new NullParameterException("personHasAddress", null);
        }
        return (PersonHasAddress) saveEntity(personHasAddress);
    }
    
    @Override
    public Long countAddressByPerson(long personId) throws GeneralException, NullParameterException {
        List result = null;
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(pha.id) FROM personHasAddress pha WHERE pha.personId = ?1");
        Query query = null;
        try {
            query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", personId);
            result = (List) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0) != null ? (Long) result.get(0) : 0l;
    }

    //FamilyReferences
    @Override
    public List<FamilyReferences> getFamilyReferences(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<FamilyReferences> familyReferences = (List<FamilyReferences>) listEntities(FamilyReferences.class, request, logger, getMethodName());
        return familyReferences;
    }

    @Override
    public List<FamilyReferences> getFamilyReferencesByApplicant(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<FamilyReferences> familyReferencesByApplicantList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID), null);
        }
        familyReferencesByApplicantList = (List<FamilyReferences>) getNamedQueryResult(UtilsEJB.class, QueryConstants.FAMILY_REFERENCES_BY_APPLICANT, request, getMethodName(), logger, "familyReferencesByApplicantList");
        return familyReferencesByApplicantList;
    }

    @Override
    public List<FamilyReferences> getFamilyReferencesByCustomer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<FamilyReferences> familyReferencesByCustomerList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_NATURAL_CUSTOMER_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_NATURAL_CUSTOMER_ID), null);
        }
        familyReferencesByCustomerList = (List<FamilyReferences>) getNamedQueryResult(UtilsEJB.class, QueryConstants.FAMILY_REFERENCES_BY_CUSTOMER, request, getMethodName(), logger, "familyReferencesByCustomerList");
        return familyReferencesByCustomerList;
    }

    @Override
    public FamilyReferences loadFamilyReferences(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        FamilyReferences familyReferences = (FamilyReferences) loadEntity(FamilyReferences.class, request, logger, getMethodName());
        return familyReferences;
    }

    @Override
    public FamilyReferences saveFamilyReferences(FamilyReferences familyReferences) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (familyReferences == null) {
            throw new NullParameterException("familyReferences", null);
        }
        return (FamilyReferences) saveEntity(familyReferences);
    }

    //PhoneType
    @Override
    public List<PhoneType> getPhoneType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PhoneType> phoneType = (List<PhoneType>) listEntities(PhoneType.class, request, logger, getMethodName());
        return phoneType;
    }

    @Override
    public PhoneType loadPhoneType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PhoneType phoneType = (PhoneType) loadEntity(PhoneType.class, request, logger, getMethodName());
        return phoneType;
    }

    @Override
    public PhoneType savePhoneType(PhoneType phoneType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (phoneType == null) {
            throw new NullParameterException("phoneType", null);
        }
        return (PhoneType) saveEntity(phoneType);
    }

    //ApplicantNaturalPerson
    @Override
    public List<ApplicantNaturalPerson> getApplicantNaturalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApplicantNaturalPerson> applicantNaturalPerson = (List<ApplicantNaturalPerson>) listEntities(ApplicantNaturalPerson.class, request, logger, getMethodName());
        return applicantNaturalPerson;
    }

    @Override
    public ApplicantNaturalPerson loadApplicantNaturalPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ApplicantNaturalPerson applicantNaturalPerson = (ApplicantNaturalPerson) loadEntity(ApplicantNaturalPerson.class, request, logger, getMethodName());
        return applicantNaturalPerson;
    }

    @Override
    public ApplicantNaturalPerson saveApplicantNaturalPerson(ApplicantNaturalPerson applicantNaturalPerson) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (applicantNaturalPerson == null) {
            throw new NullParameterException("applicantNaturalPerson", null);
        }
        return (ApplicantNaturalPerson) saveEntity(applicantNaturalPerson);
    }

    @Override
    public List<ApplicantNaturalPerson> getCardComplementaryByApplicant(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApplicantNaturalPerson> cardComplementaryByApplicantList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID), null);
        }
        cardComplementaryByApplicantList = (List<ApplicantNaturalPerson>) getNamedQueryResult(UtilsEJB.class, QueryConstants.CARD_COMPLEMENTARY_BY_APPLICANT, request, getMethodName(), logger, "cardComplementaryByApplicantList");
        return cardComplementaryByApplicantList;
    }
    
    @Override
    public List<ApplicantNaturalPerson> searchCardComplementaryByApplicant(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApplicantNaturalPerson> applicantNaturalPersonList= null; 
               
        Map<String, Object> params = request.getParams();       
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID), null);
        }
        
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_NAME)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_NAME), null);
        }
          
        try {
              
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM applicantNaturalPerson a WHERE a.applicantParentId=");
            sqlBuilder.append(params.get(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID));
            sqlBuilder.append(" and (a.firstNames");
            sqlBuilder.append(" like '%");
            sqlBuilder.append(params.get(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_NAME));
            sqlBuilder.append("%' or a.lastNames");
            sqlBuilder.append(" like '%");
            sqlBuilder.append(params.get(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_NAME));
            sqlBuilder.append("%')");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), ApplicantNaturalPerson.class);
            applicantNaturalPersonList = (List<ApplicantNaturalPerson>) query.setHint("toplink.refresh", "true").getResultList();
                          
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        
        return applicantNaturalPersonList;   
     }
    
    
        @Override
    public List<ApplicantNaturalPerson> searchCardComplementaryByApplicantOFAC(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApplicantNaturalPerson> applicantNaturalPersonList= null; 
               
        Map<String, Object> params = request.getParams();       
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID), null);
        }
        
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_NAME)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_NAME), null);
        }
          
        try {
              
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM applicantNaturalPerson a WHERE (a.applicantParentId=");
            sqlBuilder.append(params.get(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID));
            sqlBuilder.append(" or a.id=");
            sqlBuilder.append(params.get(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID));
            sqlBuilder.append(") and (a.firstNames");
            sqlBuilder.append(" like '%");
            sqlBuilder.append(params.get(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_NAME));
            sqlBuilder.append("%' or a.lastNames");
            sqlBuilder.append(" like '%");
            sqlBuilder.append(params.get(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_NAME));
            sqlBuilder.append("%')");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), ApplicantNaturalPerson.class);
            applicantNaturalPersonList = (List<ApplicantNaturalPerson>) query.setHint("toplink.refresh", "true").getResultList();
                          
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        
        return applicantNaturalPersonList;   
     }

    @Override
    public List<ApplicantNaturalPerson> getApplicantByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApplicantNaturalPerson> applicantByPersonList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        applicantByPersonList = (List<ApplicantNaturalPerson>) getNamedQueryResult(ApplicantNaturalPerson.class, QueryConstants.APPLICANT_BY_PERSON, request, getMethodName(), logger, "applicantByPersonList");
        return applicantByPersonList;
    }
    
    public List<ApplicantNaturalPerson> getApplicantByRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ApplicantNaturalPerson> cardComplementaryByApplicantList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_REQUEST_ID), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_STATUS_APPLICANT_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_STATUS_APPLICANT_ID), null);
        }
        cardComplementaryByApplicantList = (List<ApplicantNaturalPerson>) getNamedQueryResult(UtilsEJB.class, QueryConstants.APPLICANT_BY_REQUEST, request, getMethodName(), logger, "applicantByRequest");
        return cardComplementaryByApplicantList;
    }
    
    @Override
    public Long countCardComplementaryByApplicant(long applicantNaturalPersonId) throws GeneralException, NullParameterException {
        List result = null;
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(a.id) FROM applicantNaturalPerson a WHERE a.applicantParentId = ?1");
        Query query = null;
        try {
            query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", applicantNaturalPersonId);
            result = (List) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0) != null ? (Long) result.get(0) : 0l;
    }

    //KinShipApplicant
    @Override
    public List<KinShipApplicant> getKinShipApplicant(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<KinShipApplicant> kinShipApplicant = (List<KinShipApplicant>) listEntities(KinShipApplicant.class, request, logger, getMethodName());
        return kinShipApplicant;
    }

    @Override
    public KinShipApplicant loadKinShipApplicant(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        KinShipApplicant kinShipApplicant = (KinShipApplicant) loadEntity(KinShipApplicant.class, request, logger, getMethodName());
        return kinShipApplicant;

    }

    @Override
    public KinShipApplicant saveKinShipApplicant(KinShipApplicant kinShipApplicant) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (kinShipApplicant == null) {
            throw new NullParameterException("kinShipApplicant", null);
        }
        return (KinShipApplicant) saveEntity(kinShipApplicant);
    }

    @Override
    public List<KinShipApplicant> getKinShipApplicantByLanguage(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<KinShipApplicant> kinShipApplicants = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_LANGUAGE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        kinShipApplicants = (List<KinShipApplicant>) getNamedQueryResult(UtilsEJB.class, "KinShipApplicant.findByLanguageId", request, getMethodName(), logger, "kinShipApplicants");
        return kinShipApplicants;
    }

    //CivilStatus
    @Override
    public List<CivilStatus> getCivilStatus(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CivilStatus> civilStatus = (List<CivilStatus>) listEntities(CivilStatus.class, request, logger, getMethodName());
        return civilStatus;
    }

    @Override
    public CivilStatus loadCivilStatus(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CivilStatus civilStatuses = (CivilStatus) loadEntity(CivilStatus.class, request, logger, getMethodName());
        return civilStatuses;
    }

    @Override
    public CivilStatus saveCivilStatus(CivilStatus civilStatus) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (civilStatus == null) {
            throw new NullParameterException("civilStatus", null);
        }
        return (CivilStatus) saveEntity(civilStatus);
    }

    @Override
    public List<CivilStatus> getCivilStatusByLanguage(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CivilStatus> civilStatuses = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_LANGUAGE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        civilStatuses = (List<CivilStatus>) getNamedQueryResult(UtilsEJB.class, "CivilStatus.findByLanguageId", request, getMethodName(), logger, "civilStatuses");
        return civilStatuses;
    }

    //Profession
    @Override
    public List<Profession> getProfession(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Profession> profession = (List<Profession>) listEntities(Profession.class, request, logger, getMethodName());
        return profession;
    }

    @Override
    public Profession loadProfession(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Profession profession = (Profession) loadEntity(Profession.class, request, logger, getMethodName());
        return profession;
    }

    @Override
    public Profession saveProfession(Profession profession) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (profession == null) {
            throw new NullParameterException("profession", null);
        }
        return (Profession) saveEntity(profession);
    }

    //LegalPersonHasLegalRepresentatives
    @Override
    public List<LegalPersonHasLegalRepresentatives> getLegalPersonHasLegalRepresentativeses(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPersonHasLegalRepresentatives> legalPersonHasLegalRepresentatives = (List<LegalPersonHasLegalRepresentatives>) listEntities(LegalPersonHasLegalRepresentatives.class, request, logger, getMethodName());
        return legalPersonHasLegalRepresentatives;
    }

    @Override
    public List<LegalPersonHasLegalRepresentatives> getLegalRepresentativesesBylegalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPersonHasLegalRepresentatives> legalRepresentativesByApplicantList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_LEGAL_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_LEGAL_PERSON_ID), null);
        }
        legalRepresentativesByApplicantList = (List<LegalPersonHasLegalRepresentatives>) getNamedQueryResult(LegalPersonHasLegalRepresentatives.class, QueryConstants.LEGAL_REPRESENTATIVES_BY_LEGAL_APPLICANT, request, getMethodName(), logger, "legalRepresentativesByApplicantList");
        return legalRepresentativesByApplicantList;
    }

    @Override
    public LegalPersonHasLegalRepresentatives loadLegalPersonHasLegalRepresentatives(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LegalPersonHasLegalRepresentatives legalPersonHasLegalRepresentatives = (LegalPersonHasLegalRepresentatives) loadEntity(LegalPersonHasLegalRepresentatives.class, request, logger, getMethodName());
        return legalPersonHasLegalRepresentatives;
    }

    @Override
    public LegalPersonHasLegalRepresentatives saveLegalPersonHasLegalRepresentatives(LegalPersonHasLegalRepresentatives legalPersonHasLegalRepresentatives) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (legalPersonHasLegalRepresentatives == null) {
            throw new NullParameterException("legalPersonHasLegalRepresentatives", null);
        }
        return (LegalPersonHasLegalRepresentatives) saveEntity(legalPersonHasLegalRepresentatives);
    }

    //PersonType
    @Override
    public List<PersonType> getPersonType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonType> personTypeList = (List<PersonType>) listEntities(PersonType.class, request, logger, getMethodName());
        return personTypeList;
    }

    @Override
    public PersonType loadPersonType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PersonType personType = (PersonType) loadEntity(PersonType.class, request, logger, getMethodName());
        return personType;
    }

    @Override
    public PersonType savePersonType(PersonType personType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (personType == null) {
            throw new NullParameterException("personType", null);
        }
        return (PersonType) saveEntity(personType);
    }

    //DocumentsPersonType
    @Override
    public List<DocumentsPersonType> getDocumentsPersonType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DocumentsPersonType> documentsPersonTypeList = (List<DocumentsPersonType>) listEntities(DocumentsPersonType.class, request, logger, getMethodName());
        return documentsPersonTypeList;
    }

    @Override
    public DocumentsPersonType loadDocumentsPersonType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        DocumentsPersonType documentsPersonType = (DocumentsPersonType) loadEntity(DocumentsPersonType.class, request, logger, getMethodName());
        return documentsPersonType;
    }

    @Override
    public DocumentsPersonType saveDocumentsPersonType(DocumentsPersonType documentsPersonType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (documentsPersonType == null) {
            throw new NullParameterException("documentsPersonType", null);
        }
        return (DocumentsPersonType) saveEntity(documentsPersonType);
    }

    @Override
    public List<DocumentsPersonType> getDocumentsPersonTypeByPersonType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<DocumentsPersonType> documentsPersonTypes = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_TYPE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        documentsPersonTypes = (List<DocumentsPersonType>) getNamedQueryResult(UtilsEJB.class, "DocumentsPersonType.findByPersonType", request, getMethodName(), logger, "documentsPersonTypes");
        return documentsPersonTypes;
    }

    //CardRequestNaturalPerson
    @Override
    public List<CardRequestNaturalPerson> getCardRequestNaturalPersons(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRequestNaturalPerson> cardRequestNaturalPersons = (List<CardRequestNaturalPerson>) listEntities(CardRequestNaturalPerson.class, request, logger, getMethodName());
        return cardRequestNaturalPersons;
    }

    @Override
    public List<CardRequestNaturalPerson> getCardRequestNaturalPersonsByLegalApplicant(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRequestNaturalPerson> cardRequestNaturalPersonList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_LEGAL_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_APPLICANT_LEGAL_PERSON_ID), null);
        }
        cardRequestNaturalPersonList = (List<CardRequestNaturalPerson>) getNamedQueryResult(CardRequestNaturalPerson.class, QueryConstants.ADDITIONAL_CARD_BY_LEGAL_APPLICANT, request, getMethodName(), logger, "cardRequestNaturalPersonList");
        return cardRequestNaturalPersonList;
    }

    @Override
    public List<CardRequestNaturalPerson> getCardRequestNaturalPersonsByLegalCustomer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRequestNaturalPerson> cardRequestNaturalPersonByLegalCustomer = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_LEGAL_CUSTOMER_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_LEGAL_CUSTOMER_ID), null);
        }
        cardRequestNaturalPersonByLegalCustomer = (List<CardRequestNaturalPerson>) getNamedQueryResult(CardRequestNaturalPerson.class, QueryConstants.ADDITIONAL_CARD_BY_LEGAL_CUSTOMER, request, getMethodName(), logger, "cardRequestNaturalPersonByLegalCustomer");
        return cardRequestNaturalPersonByLegalCustomer;
    }

    @Override
    public CardRequestNaturalPerson loadCardRequestNaturalPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        CardRequestNaturalPerson cardRequestNaturalPerson = (CardRequestNaturalPerson) loadEntity(CardRequestNaturalPerson.class, request, logger, getMethodName());
        return cardRequestNaturalPerson;
    }

    @Override
    public CardRequestNaturalPerson saveCardRequestNaturalPerson(CardRequestNaturalPerson cardRequestNaturalPerson) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (cardRequestNaturalPerson == null) {
            throw new NullParameterException("cardRequestNaturalPerson", null);
        }
        return (CardRequestNaturalPerson) saveEntity(cardRequestNaturalPerson);
    }
    
    public Long cardRequestNaturalPersonBlackList(Long legalpersonId) throws GeneralException, NullParameterException {
        int statusApplicantBlackList = StatusApplicantE.LISNEG.getId();
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(cr.id) FROM cardRequestNaturalPerson cr WHERE cr.statusApplicantId = ?1 AND cr.legalPersonid = ?2");
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        query.setParameter("1", statusApplicantBlackList);
        query.setParameter("2", legalpersonId);
        List result = (List) query.setHint("toplink.refresh", "true").getResultList();
        return result.get(0) != null ? (Long) result.get(0) : 0l;
    }
    
    
    //Person
    @Override
    public List<Person> getPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Person> person = (List<Person>) listEntities(Person.class, request, logger, getMethodName());
        return person;
    }

    @Override
    public List<Person> getPersonByClassification(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Person> personByCustommer = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }
        personByCustommer = (List<Person>) getNamedQueryResult(Person.class, QueryConstants.PERSON_BY_CLASIFICATION, request, getMethodName(), logger, "personByCustommer");
        return personByCustommer;
    }
    
        @Override
    public List<Person> searchPersonByClassification(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Person> personList= null; 
               
        Map<String, Object> params = request.getParams();       
         if (!params.containsKey(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        } 
        
        if (!params.containsKey(EjbConstants.PARAM_PERSON_NAME)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_NAME), null);
        }  
        
          try {
              
            StringBuilder sqlBuilder = new StringBuilder("select t.id, t.countryId, t.email, t.createDate, t.updateDate, t.personClassificationId, t.personTypeId from (SELECT p.*, concat(n.firstNames, ' ', n.firstNames) as nameFull ");
            sqlBuilder.append("FROM person p join naturalCustomer n on p.id=n.personId and p.personClassificationId=");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID));
            sqlBuilder.append(" union SELECT p.*, l.enterpriseName as nameFull FROM person p join legalCustomer l on p.id=l.personId and p.personClassificationId=");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID));
            sqlBuilder.append(") as t where t.nameFull like '%");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_NAME));
            sqlBuilder.append("%'");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Person.class);
            personList = (List<Person>) query.setHint("toplink.refresh", "true").getResultList();
                          
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return personList;   

    }

    @Override
    public Person loadPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Person person = (Person) loadEntity(Person.class, request, logger, getMethodName());
        return person;
    }

    @Override
    public Person savePerson(Person person) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (person == null) {
            throw new NullParameterException("person", null);
        }
        return (Person) saveEntity(person);

    }

    //NaturalPerson
    @Override
    public List<NaturalPerson> getNaturalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<NaturalPerson> naturalPerson = (List<NaturalPerson>) listEntities(NaturalPerson.class, request, logger, getMethodName());
        return naturalPerson;
    }

    @Override
    public List<NaturalPerson> getNaturalPersonByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<NaturalPerson> naturalPersonByPerson = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        naturalPersonByPerson = (List<NaturalPerson>) getNamedQueryResult(NaturalPerson.class, QueryConstants.NATURAL_PERSON_BY_PERSON, request, getMethodName(), logger, "naturalPersonByPerson");
        return naturalPersonByPerson;
    }

    @Override
    public NaturalPerson loadNaturalPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        NaturalPerson naturalPerson = (NaturalPerson) loadEntity(NaturalPerson.class, request, logger, getMethodName());
        return naturalPerson;
    }

    @Override
    public NaturalPerson saveNaturalPerson(NaturalPerson naturalPerson) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (naturalPerson == null) {
            throw new NullParameterException("naturalPerson", null);
        }
        return (NaturalPerson) saveEntity(naturalPerson);
    }

    //Issuer
    @Override
    public List<Issuer> getIssuer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Issuer> issuer = (List<Issuer>) listEntities(Issuer.class, request, logger, getMethodName());
        return issuer;
    }
    
    public List<Issuer> getIssuerByCountry(EJBRequest request)throws EmptyListException, GeneralException, NullParameterException{
        List<Issuer> issuerByCountry = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
        issuerByCountry = (List<Issuer>) getNamedQueryResult(Issuer.class, QueryConstants.ISSUER_BY_COUNTRY, request, getMethodName(), logger, "issuerByCountry");
        return issuerByCountry;
    }

    @Override
    public Issuer loadIssuer(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Issuer issuer = (Issuer) loadEntity(Issuer.class, request, logger, getMethodName());
        return issuer;
    }

    @Override
    public Issuer saveIssuer(Issuer issuer) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (issuer == null) {
            throw new NullParameterException("issuer", null);
        }
        return (Issuer) saveEntity(issuer);
    }

    //IssuerType
    @Override
    public List<IssuerType> getIssuerType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<IssuerType> issuerType = (List<IssuerType>) listEntities(IssuerType.class, request, logger, getMethodName());
        return issuerType;
    }

    @Override
    public IssuerType loadIssuerType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        IssuerType issuerType = (IssuerType) loadEntity(IssuerType.class, request, logger, getMethodName());
        return issuerType;
    }

    @Override
    public IssuerType saveIssuerType(IssuerType issuerType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (issuerType == null) {
            throw new NullParameterException("issuerType", null);
        }
        return (IssuerType) saveEntity(issuerType);
    }

    //NaturalCustomer
    @Override
    public List<NaturalCustomer> getNaturalCustomer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<NaturalCustomer> naturalCustomer = (List<NaturalCustomer>) listEntities(NaturalCustomer.class, request, logger, getMethodName());
        return naturalCustomer;
    }

    @Override
    public List<NaturalCustomer> getNaturalCustomerByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<NaturalCustomer> naturalCustomerByPerson = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        naturalCustomerByPerson = (List<NaturalCustomer>) getNamedQueryResult(NaturalCustomer.class, QueryConstants.NATURAL_CUSTOMER_BY_PERSON, request, getMethodName(), logger, "naturalCustomerByPerson");
        return naturalCustomerByPerson;
    }

    @Override
    public List<NaturalCustomer> getNaturalCustomerByCardComplementaries(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<NaturalCustomer> naturalCustomerByCardComplementaries = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_NATURAL_CUSTOMER_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_NATURAL_CUSTOMER_ID), null);
        }
        naturalCustomerByCardComplementaries = (List<NaturalCustomer>) getNamedQueryResult(NaturalCustomer.class, QueryConstants.NATURAL_CUSTOMER_BY_CARD_COMPLEMENTARIES, request, getMethodName(), logger, "naturalCustomerByCardComplementaries");
        return naturalCustomerByCardComplementaries;
    }

    @Override
    public NaturalCustomer loadNaturalCustomer(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        NaturalCustomer naturalCustomer = (NaturalCustomer) loadEntity(NaturalCustomer.class, request, logger, getMethodName());
        return naturalCustomer;
    }

    @Override
    public NaturalCustomer saveNaturalCustomer(NaturalCustomer naturalCustomer) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (naturalCustomer == null) {
            throw new NullParameterException("naturalCustomer", null);
        }
        return (NaturalCustomer) saveEntity(naturalCustomer);
    }
    
    @Override
    public NaturalCustomer getNaturalCustomerByIdentificationNumber(Integer identificationNumber) throws RegisterNotFoundException, NullParameterException, GeneralException, InvalidQuestionException { 
        NaturalCustomer naturalCustomer = new NaturalCustomer();
        try {
            if (identificationNumber == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT n FROM NaturalCustomer n ");
            sqlBuilder.append("WHERE n.identificationNumber = '").append(identificationNumber).append("'");
            naturalCustomer = (NaturalCustomer) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();
            
        } catch (NoResultException ex) {
            return naturalCustomer = null;
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return naturalCustomer;
        
    }

    public NaturalCustomer validateQuestionNatural(Long personId, String identificationNumber, Date dateBirth) throws RegisterNotFoundException, NullParameterException, GeneralException, InvalidQuestionException {
        try {
            //Se obtiene el cliente
            Query query = entityManager.createQuery("SELECT n FROM NaturalCustomer n WHERE n.personId.id = :personId");
            query.setParameter("personId", personId);
            NaturalCustomer naturalCustomer = (NaturalCustomer) query.getSingleResult();
            
            //Se validan las respuestas en BD
            if (!naturalCustomer.getIdentificationNumber().equals(identificationNumber)) {
                throw new InvalidQuestionException(com.cms.commons.util.Constants.INVALID_QUESTION_EXCEPTION);
            }
            if (!naturalCustomer.getDateBirth().equals(dateBirth)) {
                throw new InvalidQuestionException(com.cms.commons.util.Constants.INVALID_QUESTION_EXCEPTION);
            }
            return naturalCustomer;
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
    }

    //LegalCustomer
    @Override
    public List<LegalCustomer> getLegalCustomer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalCustomer> legalCustomer = (List<LegalCustomer>) listEntities(LegalCustomer.class, request, logger, getMethodName());
        return legalCustomer;
    }

    @Override
    public List<LegalCustomer> getLegalCustomerByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalCustomer> legalCustomerByPerson = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        legalCustomerByPerson = (List<LegalCustomer>) getNamedQueryResult(LegalCustomer.class, QueryConstants.LEGAL_PERSON_BY_CUSTOMER, request, getMethodName(), logger, "legalCustomerByPerson");
        return legalCustomerByPerson;
    }

    @Override
    public LegalCustomer loadLegalCustomer(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LegalCustomer legalCustomer = (LegalCustomer) loadEntity(LegalCustomer.class, request, logger, getMethodName());
        return legalCustomer;
    }

    @Override
    public LegalCustomer saveLegalCustomer(LegalCustomer legalCustomer) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (legalCustomer == null) {
            throw new NullParameterException("legalCustomer", null);
        }
        return (LegalCustomer) saveEntity(legalCustomer);
    }
    
    
    public LegalCustomer validateQuestionLegal(Long personId, String identificationNumber, Date dateInscriptionRegister) throws RegisterNotFoundException, NullParameterException, GeneralException, InvalidQuestionException {
        try {
            //Se obtiene el cliente
            Query query = entityManager.createQuery("SELECT l FROM LegalCustomer l WHERE l.personId.id = :personId");
            query.setParameter("personId", personId);
            LegalCustomer legalCustomer = (LegalCustomer) query.getSingleResult();
            
            //Se validan las respuestas en BD
            if (!legalCustomer.getIdentificationNumber().equals(identificationNumber)) {
                throw new InvalidQuestionException(com.cms.commons.util.Constants.INVALID_QUESTION_EXCEPTION);
            }
            if (!legalCustomer.getDateInscriptionRegister().equals(dateInscriptionRegister)) {
                throw new InvalidQuestionException(com.cms.commons.util.Constants.INVALID_QUESTION_EXCEPTION);
            }
            return legalCustomer;
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
    }    

    //PlasticManufacturer
    @Override
    public List<PlasticManufacturer> getPlasticManufacturer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PlasticManufacturer> plasticManufacturer = (List<PlasticManufacturer>) listEntities(PlasticManufacturer.class, request, logger, getMethodName());
        return plasticManufacturer;
    }

    @Override
    public PlasticManufacturer loadPlasticManufacturer(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PlasticManufacturer plasticManufacturer = (PlasticManufacturer) loadEntity(PlasticManufacturer.class, request, logger, getMethodName());
        return plasticManufacturer;
    }

    @Override
    public PlasticManufacturer savePlasticManufacturer(PlasticManufacturer plasticManufacturer) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (plasticManufacturer == null) {
            throw new NullParameterException("plasticManufacturer", null);
        }
        return (PlasticManufacturer) saveEntity(plasticManufacturer);
    }

    //StatusCustomer
    @Override
    public List<StatusCustomer> getStatusCustomer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<StatusCustomer> statusCustomer = (List<StatusCustomer>) listEntities(StatusCustomer.class, request, logger, getMethodName());
        return statusCustomer;
    }

    @Override
    public StatusCustomer loadStatusCustomer(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusCustomer statusCustomer = (StatusCustomer) loadEntity(StatusCustomer.class, request, logger, getMethodName());
        return statusCustomer;
    }

    @Override
    public StatusCustomer saveStatusCustomer(StatusCustomer statusCustomer) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (statusCustomer == null) {
            throw new NullParameterException("statusCustomer", null);
        }
        return (StatusCustomer) saveEntity(statusCustomer);
    }

    //LegalCustomerHasLegalRepresentatives
    @Override
    public List<LegalCustomerHasLegalRepresentatives> getLegalCustomerHasLegalRepresentatives(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalCustomerHasLegalRepresentatives> legalCustomerHasLegalRepresentatives = (List<LegalCustomerHasLegalRepresentatives>) listEntities(LegalCustomerHasLegalRepresentatives.class, request, logger, getMethodName());
        return legalCustomerHasLegalRepresentatives;
    }

    @Override
    public List<LegalCustomerHasLegalRepresentatives> getLegalRepresentativesesByCustomer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalCustomerHasLegalRepresentatives> legalRepresentativesByCustomer = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_LEGAL_CUSTOMER_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_LEGAL_CUSTOMER_ID), null);
        }
        legalRepresentativesByCustomer = (List<LegalCustomerHasLegalRepresentatives>) getNamedQueryResult(LegalPersonHasLegalRepresentatives.class, QueryConstants.LEGAL_REPRESENTATIVES_BY_LEGAL_CUSTOMER, request, getMethodName(), logger, "legalRepresentativesByCustomer");
        return legalRepresentativesByCustomer;
    }

    @Override
    public LegalCustomerHasLegalRepresentatives loadLegalCustomerHasLegalRepresentatives(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LegalCustomerHasLegalRepresentatives legalCustomerHasLegalRepresentatives = (LegalCustomerHasLegalRepresentatives) loadEntity(LegalCustomerHasLegalRepresentatives.class, request, logger, getMethodName());
        return legalCustomerHasLegalRepresentatives;
    }

    @Override
    public LegalCustomerHasLegalRepresentatives saveLegalCustomerHasLegalRepresentatives(LegalCustomerHasLegalRepresentatives legalCustomerHasLegalRepresentatives) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (legalCustomerHasLegalRepresentatives == null) {
            throw new NullParameterException("legalCustomerHasLegalRepresentatives", null);
        }
        return (LegalCustomerHasLegalRepresentatives) saveEntity(legalCustomerHasLegalRepresentatives);
    }

    //AdditionalInformationNaturalCustomer
    @Override
    public List<AdditionalInformationNaturalCustomer> getAdditionalInformationNaturalCustomer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AdditionalInformationNaturalCustomer> additionalInformationNaturalCustomer = (List<AdditionalInformationNaturalCustomer>) listEntities(AdditionalInformationNaturalCustomer.class, request, logger, getMethodName());
        return additionalInformationNaturalCustomer;
    }

    @Override
    public List<AdditionalInformationNaturalCustomer> getAdditionalInformationNaturalCustomeByCustomer(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<AdditionalInformationNaturalCustomer> additionalInformationNaturalCustomeByCustomer = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_NATURAL_CUSTOMER_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_NATURAL_CUSTOMER_ID), null);
        }
        additionalInformationNaturalCustomeByCustomer = (List<AdditionalInformationNaturalCustomer>) getNamedQueryResult(AdditionalInformationNaturalCustomer.class, QueryConstants.ADDITIONAL_INFORMATION_BY_CUSTOMER, request, getMethodName(), logger, "additionalInformationNaturalCustomeByCustomer");
        return additionalInformationNaturalCustomeByCustomer;
    }

    @Override
    public AdditionalInformationNaturalCustomer loadAdditionalInformationNaturalCustomer(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        AdditionalInformationNaturalCustomer additionalInformationNaturalCustomer = (AdditionalInformationNaturalCustomer) loadEntity(AdditionalInformationNaturalCustomer.class, request, logger, getMethodName());
        return additionalInformationNaturalCustomer;
    }

    @Override
    public AdditionalInformationNaturalCustomer saveAdditionalInformationNaturalCustomer(AdditionalInformationNaturalCustomer additionalInformationNaturalCustomer) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (additionalInformationNaturalCustomer == null) {
            throw new NullParameterException("additionalInformationNaturalCustomer", null);
        }
        return (AdditionalInformationNaturalCustomer) saveEntity(additionalInformationNaturalCustomer);
    }

    //User
    @Override
    public List<User> getUser(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<User> user = (List<User>) listEntities(User.class, request, logger, getMethodName());
        return user;
    }

    @Override
    public User loadUser(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        User user = (User) loadEntity(User.class, request, logger, getMethodName());
        return user;
    }

    @Override
    public User saveUser(User user) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (user == null) {
            throw new NullParameterException("user", null);
        }
        return (User) saveEntity(user);
    }
    
    @Override
    public List<User> validatePassword(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<User> userList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_CURRENT_PASSWORD)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_CURRENT_PASSWORD), null);
        }
        if (!params.containsKey(EjbConstants.PARAM_USER_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_USER_ID), null);
        }        
        userList = (List<User>) getNamedQueryResult(User.class, QueryConstants.VALIDATE_PASSWORD, request, getMethodName(), logger, "userList");
        return userList;
    }
    
    @Override
    public List<User> getUserByLogin(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<User> userList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_USER)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_USER), null);
        }       
        userList = (List<User>) getNamedQueryResult(User.class, QueryConstants.LOGIN_EXIST_IN_BD, request, getMethodName(), logger, "userList");
        return userList;
    }
    
    @Override
    public List<User> getValidateEmployee(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<User> userList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_EMPLOYEE)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_EMPLOYEE), null);
        }       
        userList = (List<User>) getNamedQueryResult(User.class, QueryConstants.EMPLOYEE_EXIST_IN_BD, request, getMethodName(), logger, "userList");
        return userList;
    }
    
    @Override
    public List<User> searchUser(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {          
        List<User> userList = null;        
        Map<String, Object> params = request.getParams();      
        if (!params.containsKey(EjbConstants.PARAM_USER)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_USER), null);
        }        
        try {
           userList = (List<User>) getNamedQueryResult(User.class, QueryConstants.USER_LIKE , request, getMethodName(), logger, "userList");
        } catch (Exception e) {
            e.getMessage();
            e.getLocalizedMessage();
            e.printStackTrace();
        }
        return userList;
        
    }
    
    public List<User> searchUserByIdentificationNumber(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<User> userList= null; 
               
        Map<String, Object> params = request.getParams();       
        if (!params.containsKey(EjbConstants.PARAM_IDENTIFICATION_NUMBER)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }  
        
          try {
            StringBuilder sqlBuilder = new StringBuilder("select * from user u where u.identificationNumber =");
            sqlBuilder.append(params.get(EjbConstants.PARAM_IDENTIFICATION_NUMBER));
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Person.class);
            userList = (List<User>) query.setHint("toplink.refresh", "true").getResultList();                      
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return userList;   
    }

    //Employee
    @Override
    public List<Employee> getEmployee(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Employee> employee = (List<Employee>) listEntities(Employee.class, request, logger, getMethodName());
        return employee;
    }

    @Override
    public Employee loadEmployee(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Employee employee = (Employee) loadEntity(Employee.class, request, logger, getMethodName());
        return employee;
    }

    @Override
    public Employee saveEmployee(Employee employee) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (employee == null) {
            throw new NullParameterException("employee", null);
        }
        return (Employee) saveEntity(employee);
    }
    
    public List<Employee> getSearchEmployee(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<Employee> employeeList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT e FROM Employee e ");
            sqlBuilder.append("WHERE e.lastNames LIKE '").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            employeeList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return employeeList;
    }

    //ComercialAgency
    @Override
    public List<ComercialAgency> getComercialAgency(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<ComercialAgency> comercialAgency = (List<ComercialAgency>) listEntities(ComercialAgency.class, request, logger, getMethodName());
        return comercialAgency;
    }

    @Override
    public ComercialAgency loadComercialAgency(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ComercialAgency comercialAgency = (ComercialAgency) loadEntity(ComercialAgency.class, request, logger, getMethodName());
        return comercialAgency;
    }

    @Override
    public ComercialAgency saveComercialAgency(ComercialAgency comercialAgency) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (comercialAgency == null) {
            throw new NullParameterException("comercialAgency", null);
        }
        return (ComercialAgency) saveEntity(comercialAgency);
    }

    //LegalPerson
    @Override
    public List<LegalPerson> getLegalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPerson> legalPersonList = (List<LegalPerson>) listEntities(LegalPerson.class, request, logger, getMethodName());
        return legalPersonList;
    }

    @Override
    public LegalPerson loadLegalPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LegalPerson legalPerson = (LegalPerson) loadEntity(LegalPerson.class, request, logger, getMethodName());
        return legalPerson;
    }

    @Override
    public LegalPerson saveLegalegalPerson(LegalPerson legalPerson) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (legalPerson == null) {
            throw new NullParameterException("legalPerson", null);
        }
        return (LegalPerson) saveEntity(legalPerson);
    }

    @Override
    public List<LegalPerson> getLegalPersonByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPerson> legalPersonList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        legalPersonList = (List<LegalPerson>) getNamedQueryResult(LegalPerson.class, QueryConstants.LEGAL_PERSON_BY_PERSON, request, getMethodName(), logger, "legalPersonList");
        return legalPersonList;
    }
    
    @Override
    public List<LegalPerson> getLegalPersonByPersonClassification(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<LegalPerson> legalPersonList = null;        
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }
        legalPersonList = (List<LegalPerson>) getNamedQueryResult(LegalPerson.class, QueryConstants.LEGAL_PERSON_BY_PERSON_CLASSIFICATION, request, getMethodName(), logger, "legalPersonList");
        return legalPersonList;
    }
    
    //PasswordChangeRequest
    
    @Override
    public List<PasswordChangeRequest> getPasswordChangeRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<PasswordChangeRequest> passwordChangeRequestList = (List<PasswordChangeRequest>) listEntities(PasswordChangeRequest.class, request, logger, getMethodName());
        return passwordChangeRequestList;
    }

    @Override
    public PasswordChangeRequest loadPasswordChangeRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PasswordChangeRequest passwordChangeRequest = (PasswordChangeRequest) loadEntity(PasswordChangeRequest.class, request, logger, getMethodName());
        return passwordChangeRequest;
    }

    @Override
    public PasswordChangeRequest savePasswordChangeRequest(PasswordChangeRequest passwordChangeRequest) throws RegisterNotFoundException, NullParameterException, GeneralException {
       if (passwordChangeRequest == null) {
            throw new NullParameterException("passwordChangeRequest", null);
        }
        return (PasswordChangeRequest) saveEntity(passwordChangeRequest);
    }
    
    public List<PasswordChangeRequest> getSearchPasswordChange(String name) throws EmptyListException, GeneralException, NullParameterException {
        List<PasswordChangeRequest> passwordChangeRequestList = null;
        if (name == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
        }
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT p FROM PasswordChangeRequest p ");
            sqlBuilder.append("WHERE p.requestNumber LIKE '%").append(name).append("%'");

            Query query = entityManager.createQuery(sqlBuilder.toString());
            passwordChangeRequestList = query.setHint("toplink.refresh", "true").getResultList();

        } catch (NoResultException ex) {
            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return passwordChangeRequestList;
    }

    @Override
    public List<Issuer> searchIssuer(String name) throws EmptyListException, GeneralException, NullParameterException {
                List<Issuer> issuerList= null; 
        try {
            if (name == null) {
                throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }
            
            StringBuilder sqlBuilder = new StringBuilder("select * from issuer p where p.name like '%");
            sqlBuilder.append(name);
            sqlBuilder.append("%'");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Issuer.class);
            issuerList = (List<Issuer>) query.setHint("toplink.refresh", "true").getResultList();
            
                } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return issuerList;
    }

    @Override
    public List<LegalPerson> searchLegalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
    
        List<LegalPerson> legalPersonList = null;        
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }
         if (!params.containsKey(EjbConstants.PARAM_PERSON_ENTERPRISE_NAME)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ENTERPRISE_NAME), null);
        }
         try {
           legalPersonList = (List<LegalPerson>) getNamedQueryResult(LegalPerson.class, QueryConstants.LEGAL_PERSON_BY_PERSON_CLASSIFICATION_LIKE , request, getMethodName(), logger, "legalPersonList");

        } catch (Exception e) {
            e.getMessage();
            e.getLocalizedMessage();
            e.printStackTrace();
        }
        return legalPersonList;
    }

    @Override
    public List<Person> searchPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<Person> personList= null; 
               
        Map<String, Object> params = request.getParams();       
        if (!params.containsKey(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }  
        
        if (!params.containsKey(EjbConstants.PARAM_PERSON_NAME)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_NAME), null);
        }  
        
          try {
              
            StringBuilder sqlBuilder = new StringBuilder("select u.id, u.countryId, u.email, u.createDate, u.updateDate, u.personClassificationId, u.personTypeId ");
            sqlBuilder.append("from (SELECT p.*, lp.enterpriseName as name FROM person p join legalPerson lp on p.id=lp.personId and p.personClassificationId=");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID));
            sqlBuilder.append(" union SELECT p.*, concat(np.firstNames, ' ', np.lastNames) as name  FROM person p join naturalPerson np on p.id = np.personId and p.personClassificationId=");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_CLASSIFICATION_ID));
            sqlBuilder.append(") u where u.name like '%");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_NAME));
            sqlBuilder.append("%'");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Person.class);
            personList = (List<Person>) query.setHint("toplink.refresh", "true").getResultList();
                          
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return personList;   
    }
    
    @Override
    public List<Person> searchPersonByApplicantNaturalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Person> personList= null;                
        Map<String, Object> params = request.getParams();       
        if (!params.containsKey(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }         
        if (!params.containsKey(EjbConstants.PARAM_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }        
        if (!params.containsKey(EjbConstants.PARAM_PERSON_NAME)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_NAME), null);
        }        
          try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT u.* ");
            sqlBuilder.append("FROM (SELECT p.*, concat(ap.firstNames, ' ', ap.lastNames) as name ");
            sqlBuilder.append("FROM person p join applicantNaturalPerson ap on p.id = ap.personId ");
            sqlBuilder.append("join request r on p.id = r.personId and r.id = "); 
            sqlBuilder.append(params.get(EjbConstants.PARAM_REQUEST_ID));
            sqlBuilder.append(" union SELECT p.*, concat(ap.firstNames, ' ', ap.lastNames) as name "); 
            sqlBuilder.append("FROM person p join applicantNaturalPerson ap on p.id = ap.personId and ap.applicantParentId = ");
            sqlBuilder.append(params.get(EjbConstants.PARAM_APPLICANT_NATURAL_PERSON_ID));
            sqlBuilder.append(") u where u.name like '");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_NAME));
            sqlBuilder.append("%'");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Person.class);
            personList = (List<Person>) query.setHint("toplink.refresh", "true").getResultList();                          
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return personList;   
    }
    
    public List<Person> searchPersonByLegalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<Person> personList= null; 
               
        Map<String, Object> params = request.getParams();       
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        } 
        
        if (!params.containsKey(EjbConstants.PARAM_REQUEST_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_CLASSIFICATION_ID), null);
        }
        
        if (!params.containsKey(EjbConstants.PARAM_PERSON_NAME)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_NAME), null);
        }    
          try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT u.* ");
            sqlBuilder.append("FROM (SELECT p.*, lp.enterpriseName as name ");
            sqlBuilder.append("FROM person p join legalPerson lp on p.id = lp.personId join request r on p.id = r.personId and r.id = ");
            sqlBuilder.append(params.get(EjbConstants.PARAM_REQUEST_ID));
            sqlBuilder.append(" union SELECT p.*, concat(lr.firstNames, ' ', lr.lastNames) as name "); 
            sqlBuilder.append("FROM person p join legalRepresentatives lr on p.id = lr.personId join legalPersonHasLegalRepresentatives lphlr on "); 
            sqlBuilder.append("lr.id = lphlr.legalRepresentatives_id and lphlr.legalPersonId = ");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_ID));
            sqlBuilder.append(" union SELECT p.*, concat(crn.firstNames, ' ', crn.lastNames) as name "); 
            sqlBuilder.append("FROM person p join cardRequestNaturalPerson crn on p.id = crn.person_id join legalPerson lp on crn.legalPerson_id = "); 
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_ID));
            sqlBuilder.append(") u where u.name like '");
            sqlBuilder.append(params.get(EjbConstants.PARAM_PERSON_NAME));
            sqlBuilder.append("%'");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString(), Person.class);
            personList = (List<Person>) query.setHint("toplink.refresh", "true").getResultList();
                          
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return personList;   
    }


    //EmployedPosition
        @Override
        public List<EmployedPosition> getEmployedPosition(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<EmployedPosition> employedPosition = (List<EmployedPosition>) listEntities(EmployedPosition.class, request, logger, getMethodName());
        return employedPosition;
    }

}
