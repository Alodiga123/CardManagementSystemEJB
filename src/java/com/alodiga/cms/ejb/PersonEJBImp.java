package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.PersonEJB;
import com.alodiga.cms.commons.ejb.PersonEJBLocal;;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
import com.cms.commons.genericEJB.AbstractDistributionEJB;
import com.cms.commons.genericEJB.DistributionContextInterceptor;
import com.cms.commons.genericEJB.DistributionLoggerInterceptor;
import com.cms.commons.genericEJB.EJBRequest;
import com.cms.commons.models.ApplicantNaturalPerson;
import com.cms.commons.models.CardRequestNaturalPerson;
import com.cms.commons.models.CivilStatus;
import com.cms.commons.models.DocumentsPersonType;
import com.cms.commons.models.FamilyReferences;
import com.cms.commons.models.KinShipApplicant;
import com.cms.commons.models.LegalPersonHasLegalRepresentatives;
import com.cms.commons.models.NaturalPerson;
import com.cms.commons.models.Person;
import com.cms.commons.models.PersonHasAddress;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.PhonePerson;
import com.cms.commons.models.PhoneType;
import com.cms.commons.models.Profession;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;

/**
 *
 * @author jose
 */
@Interceptors({DistributionLoggerInterceptor.class, DistributionContextInterceptor.class})
@Stateless(name = EjbConstants.PERSON_EJB, mappedName = EjbConstants.PERSON_EJB)
@TransactionManagement(TransactionManagementType.BEAN)

public class PersonEJBImp extends AbstractDistributionEJB implements PersonEJB , PersonEJBLocal{
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

    //PersonHasAddress
    @Override
    public List<PersonHasAddress> getPersonHasAddresses(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonHasAddress> personHasAddress = (List<PersonHasAddress>) listEntities(PersonHasAddress.class, request, logger, getMethodName());
        return personHasAddress;
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

    //FamilyReferences
    @Override
    public List<FamilyReferences> getFamilyReferences(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<FamilyReferences> familyReferences = (List<FamilyReferences>) listEntities(FamilyReferences.class, request, logger, getMethodName());
        return familyReferences;
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
    
    //CardRequestNaturalPerson
    @Override
    public List<CardRequestNaturalPerson> getCardRequestNaturalPersons(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<CardRequestNaturalPerson> cardRequestNaturalPersons = (List<CardRequestNaturalPerson>) listEntities(CardRequestNaturalPerson.class, request, logger, getMethodName());
        return cardRequestNaturalPersons;
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
    
    //Person
    @Override
    public List<Person> getPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Person> person = (List<Person>) listEntities(Person.class, request, logger, getMethodName());
        return person;
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
    
}
