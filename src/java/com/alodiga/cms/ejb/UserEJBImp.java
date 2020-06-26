package com.alodiga.cms.ejb;

import com.alodiga.cms.commons.ejb.PersonEJB;
import com.alodiga.cms.commons.ejb.PersonEJBLocal;
import com.alodiga.cms.commons.ejb.UserEJB;
import com.alodiga.cms.commons.ejb.UserEJBLocal;
import com.alodiga.cms.commons.ejb.UtilsEJB;
import com.alodiga.cms.commons.exception.EmptyListException;
import com.alodiga.cms.commons.exception.GeneralException;
import com.alodiga.cms.commons.exception.InvalidQuestionException;
import com.alodiga.cms.commons.exception.NullParameterException;
import com.alodiga.cms.commons.exception.RegisterNotFoundException;
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
import com.cms.commons.models.Permission;
import com.cms.commons.models.PermissionData;
import com.cms.commons.models.PermissionGroup;
import com.cms.commons.models.PermissionGroupData;
import com.cms.commons.models.Person;
import com.cms.commons.models.PersonHasAddress;
import com.cms.commons.models.PersonType;
import com.cms.commons.models.PhonePerson;
import com.cms.commons.models.PhoneType;
import com.cms.commons.models.PlasticManufacturer;
import com.cms.commons.models.Profession;
import com.cms.commons.models.Profile;
import com.cms.commons.models.ProfileData;
import com.cms.commons.models.StatusCustomer;
import com.cms.commons.models.User;
import com.cms.commons.models.UserHasProfile;
import com.cms.commons.util.EjbConstants;
import com.cms.commons.util.QueryConstants;
import java.util.ArrayList;
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
@Stateless(name = EjbConstants.USER_EJB, mappedName = EjbConstants.USER_EJB)
@TransactionManagement(TransactionManagementType.BEAN)

public class UserEJBImp extends AbstractDistributionEJB implements UserEJB, UserEJBLocal {

    private static final Logger logger = Logger.getLogger(UserEJBImp.class);

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
    public User loadUserByLogin(String login) throws RegisterNotFoundException, NullParameterException, GeneralException {
        User user = null;
        try {
            Query query = createQuery("SELECT u FROM User u WHERE u.login =:login AND u.enabled=TRUE");
            query.setParameter("login", login);
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(com.cms.commons.util.Constants.REGISTER_NOT_FOUND_EXCEPTION);
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(com.cms.commons.util.Constants.GENERAL_EXCEPTION);
        }
        return user;
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

    //PermissionGroup
    @Override
    public List<PermissionGroup> getPermissionGroup(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<PermissionGroup> permissionGroupList = (List<PermissionGroup>) listEntities(PermissionGroup.class, request, logger, getMethodName());
        return permissionGroupList;
    }

    @Override
    public PermissionGroup loadPermissionGroup(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PermissionGroup permissionGroup = (PermissionGroup) loadEntity(PermissionGroup.class, request, logger, getMethodName());
        return permissionGroup;
    }

    @Override
    public PermissionGroup savePermissionGroup(PermissionGroup permissionGroup) throws RegisterNotFoundException, NullParameterException, GeneralException {
         if (permissionGroup == null) {
            throw new NullParameterException("permissionGroup", null);
        }
        return (PermissionGroup) saveEntity(permissionGroup);
    }
    
    @Override
    public List<PermissionGroup> getPermissionGroups() throws EmptyListException, NullParameterException, GeneralException {
        List<PermissionGroup> permissionGroups = new ArrayList<PermissionGroup>();
        Query query = null;
        try {
            query = createQuery("SELECT pg FROM PermissionGroup pg");
            permissionGroups = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissionGroups.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissionGroups;
    }
    
    
    //PermissionGroupData
    @Override
    public List<PermissionGroupData> getPermissionGroupData(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PermissionGroupData> permissionGroupDataList = (List<PermissionGroupData>) listEntities(PermissionGroupData.class, request, logger, getMethodName());
        return permissionGroupDataList;
    }
    
    @Override
    public PermissionGroupData loadPermissionGroupData(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PermissionGroupData permissionGroupData = (PermissionGroupData) loadEntity(PermissionGroupData.class, request, logger, getMethodName());
        return permissionGroupData;
    }

    @Override
    public PermissionGroupData savePermissionGroupData(PermissionGroupData permissionGroupData) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (permissionGroupData == null) {
            throw new NullParameterException("permissionGroupData", null);
        }
        return (PermissionGroupData) saveEntity(permissionGroupData);
    }

    //Permission
    @Override
    public List<Permission> getPermission(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Permission> permissionList = (List<Permission>) listEntities(Permission.class, request, logger, getMethodName());
        return permissionList;
    }

    @Override
    public Permission loadPermission(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Permission permission = (Permission) loadEntity(Permission.class, request, logger, getMethodName());
        return permission;
    }

    @Override
    public Permission savePermission(Permission permission) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (permission == null) {
            throw new NullParameterException("permission", null);
        }
        return (Permission) saveEntity(permission);
    }
    
    @Override
    public List<Permission> getPermissions() throws EmptyListException, NullParameterException, GeneralException {
        List<Permission> permissions = new ArrayList<Permission>();
        Query query = null;
        try {
            query = createQuery("SELECT p FROM Permission p WHERE p.enabled =1");
            permissions = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    @Override
    public List<Permission> getPermissionByGroupId(Long groupId) throws EmptyListException, NullParameterException, GeneralException {
       if (groupId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "groupId"), null);
        }
        List<Permission> permissions = new ArrayList<Permission>();
        Query query = null;
        try {
            query = createQuery("SELECT p FROM Permission p WHERE p.enabled=true AND p.permissionGroupId.id=?1");
            query.setParameter("1", groupId);
            permissions = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    @Override
    public List<Permission> getPermissionByProfileId(Long profileId) throws EmptyListException, NullParameterException, GeneralException {
        if (profileId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "groupId"), null);
        }
        List<Permission> permissions = new ArrayList<Permission>();
        Query query = null;
        try {
            query = createQuery("SELECT php.permission FROM PermissionHasProfile php WHERE php.profile.id = ?1");
            query.setParameter("1", profileId);
            permissions = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    @Override
    public Permission loadPermissionById(Long permissionId) throws GeneralException, NullParameterException, RegisterNotFoundException {
        EJBRequest bRequest = new EJBRequest(permissionId);
        Permission permission = (Permission) loadEntity(Permission.class, bRequest, logger, getMethodName());
        return permission;
    }
    
    //PermissionData
    @Override
    public List<PermissionData> getPermissionData(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PermissionData> permissionDataList = (List<PermissionData>) listEntities(PermissionData.class, request, logger, getMethodName());
        return permissionDataList;
    }

    @Override
    public PermissionData loadPermissionData(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PermissionData permission = (PermissionData) loadEntity(PermissionData.class, request, logger, getMethodName());
        return permission;
    }

    @Override
    public PermissionData savePermissionData(PermissionData permissionData) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (permissionData == null) {
            throw new NullParameterException("permissionData", null);
        }
        return (PermissionData) saveEntity(permissionData);
    }
    
    //Profile    
    public List<Profile> getProfile(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<Profile> profileList = (List<Profile>) listEntities(Profile.class, request, logger, getMethodName());
        return profileList;
    }
    
    public Profile loadProfile(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Profile profile = (Profile) loadEntity(Profile.class, request, logger, getMethodName());
        return profile;
    }
    
    public Profile saveProfile(Profile profile) throws RegisterNotFoundException, NullParameterException, GeneralException {
       if (profile == null) {
            throw new NullParameterException("profile", null);
        }
        return (Profile) saveEntity(profile);
    }
    
    @Override
    public List<Profile> getProfiles() throws EmptyListException, GeneralException {
        List<Profile> profiles = new ArrayList<Profile>();
        Query query = null;
        try {
            query = createQuery("SELECT p FROM Profile p WHERE p.enabled = true");
            profiles = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (profiles.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return profiles;
    }
    
    //ProfileData
    @Override
    public List<ProfileData> getProfileData(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<ProfileData> profileDataList = (List<ProfileData>) listEntities(ProfileData.class, request, logger, getMethodName());
        return profileDataList;
    }

    @Override
    public ProfileData loadProfileData(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        ProfileData profileData = (ProfileData) loadEntity(ProfileData.class, request, logger, getMethodName());
        return profileData;
    }
    
     @Override
    public ProfileData saveProfileData(ProfileData profileData) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (profileData == null) {
            throw new NullParameterException("profileData", null);
        }
        return (ProfileData) saveEntity(profileData);
    }

    @Override
    public List<UserHasProfile> getUserHasProfile(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<UserHasProfile> userHasProfileList = (List<UserHasProfile>) listEntities(UserHasProfile.class, request, logger, getMethodName());
        return userHasProfileList;
    }

    @Override
    public UserHasProfile saveUserHasProfile(UserHasProfile userHasProfile) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (userHasProfile == null) {
            throw new NullParameterException("userHasProfile", null);
        }
        return (UserHasProfile) saveEntity(userHasProfile);  
    }

    @Override
    public UserHasProfile loadUserHasProfile(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserHasProfile> getUserHasProfileByUser(UserHasProfile userHasProfile) throws EmptyListException, GeneralException, NullParameterException {
      List<UserHasProfile> userHasProfileList = null;
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM user_has_profile where userId=")
                .append(userHasProfile.getUserId().getId().toString())
                .append(" and profileId=")
                .append(userHasProfile.getProfileId().getId().toString());
        try {
        Query query = entityManager.createNativeQuery(sqlBuilder.toString());
        userHasProfileList = (List<UserHasProfile>) query.setHint("toplink.refresh", "true").getResultList();
       
        } catch (Exception e) {
            e.getMessage();
        }
          return userHasProfileList;      
    }

}
