package com.mirzet.zukic.runtime;

import com.mirzet.zukic.runtime.model.Activity;
import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.model.DependTasks;
import com.mirzet.zukic.runtime.model.Employee;
import com.mirzet.zukic.runtime.model.Organization;
import com.mirzet.zukic.runtime.model.OrganizationType;
import com.mirzet.zukic.runtime.model.Person;
import com.mirzet.zukic.runtime.model.Project;
import com.mirzet.zukic.runtime.model.Qualification;
import com.mirzet.zukic.runtime.model.Resource;
import com.mirzet.zukic.runtime.model.Role;
import com.mirzet.zukic.runtime.model.Task;
import com.mirzet.zukic.runtime.request.ActivityCreate;
import com.mirzet.zukic.runtime.request.AppUserCreate;
import com.mirzet.zukic.runtime.request.ClientCreate;
import com.mirzet.zukic.runtime.request.DependTasksCreate;
import com.mirzet.zukic.runtime.request.EmployeeCreate;
import com.mirzet.zukic.runtime.request.OrganizationCreate;
import com.mirzet.zukic.runtime.request.OrganizationTypeCreate;
import com.mirzet.zukic.runtime.request.PersonCreate;
import com.mirzet.zukic.runtime.request.ProjectCreate;
import com.mirzet.zukic.runtime.request.QualificationCreate;
import com.mirzet.zukic.runtime.request.ResourceCreate;
import com.mirzet.zukic.runtime.request.RoleCreate;
import com.mirzet.zukic.runtime.request.TaskCreate;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import com.mirzet.zukic.runtime.service.ActivityService;
import com.mirzet.zukic.runtime.service.AppUserService;
import com.mirzet.zukic.runtime.service.ClientService;
import com.mirzet.zukic.runtime.service.DependTasksService;
import com.mirzet.zukic.runtime.service.EmployeeService;
import com.mirzet.zukic.runtime.service.OrganizationService;
import com.mirzet.zukic.runtime.service.OrganizationTypeService;
import com.mirzet.zukic.runtime.service.PersonService;
import com.mirzet.zukic.runtime.service.ProjectService;
import com.mirzet.zukic.runtime.service.QualificationService;
import com.mirzet.zukic.runtime.service.ResourceService;
import com.mirzet.zukic.runtime.service.RoleService;
import com.mirzet.zukic.runtime.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppInitConfig {

  @Autowired private PersonService personService;

  @Autowired private ResourceService resourceService;

  @Autowired private QualificationService qualificationService;

  @Autowired private ProjectService projectService;

  @Autowired private ActivityService activityService;

  @Autowired private DependTasksService dependTasksService;

  @Autowired private TaskService taskService;

  @Autowired private RoleService roleService;

  @Autowired private ClientService clientService;

  @Autowired private EmployeeService employeeService;

  @Autowired private OrganizationService organizationService;

  @Autowired private OrganizationTypeService organizationTypeService;

  @Autowired
  @Qualifier("adminSecurityContext")
  private UserSecurityContext securityContext;

  @Bean
  public Person person() {
    PersonCreate personCreate = new PersonCreate();
    return personService.createPerson(personCreate, securityContext);
  }

  @Bean
  public Resource resource() {
    ResourceCreate resourceCreate = new ResourceCreate();
    return resourceService.createResource(resourceCreate, securityContext);
  }

  @Bean
  public Qualification qualification() {
    QualificationCreate qualificationCreate = new QualificationCreate();
    return qualificationService.createQualification(qualificationCreate, securityContext);
  }

  @Bean
  public Project project() {
    ProjectCreate projectCreate = new ProjectCreate();
    return projectService.createProject(projectCreate, securityContext);
  }

  @Bean
  public Activity activity() {
    ActivityCreate activityCreate = new ActivityCreate();
    return activityService.createActivity(activityCreate, securityContext);
  }

  @Bean
  public DependTasks dependTasks() {
    DependTasksCreate dependTasksCreate = new DependTasksCreate();
    return dependTasksService.createDependTasks(dependTasksCreate, securityContext);
  }

  @Bean
  public Task task() {
    TaskCreate taskCreate = new TaskCreate();
    return taskService.createTask(taskCreate, securityContext);
  }

  @Bean
  public Role role() {
    RoleCreate roleCreate = new RoleCreate();
    return roleService.createRole(roleCreate, securityContext);
  }

  @Bean
  public Client client() {
    ClientCreate clientCreate = new ClientCreate();
    return clientService.createClient(clientCreate, securityContext);
  }

  @Bean
  public Employee employee() {
    EmployeeCreate employeeCreate = new EmployeeCreate();
    return employeeService.createEmployee(employeeCreate, securityContext);
  }

  @Bean
  public Organization organization() {
    OrganizationCreate organizationCreate = new OrganizationCreate();
    return organizationService.createOrganization(organizationCreate, securityContext);
  }

  @Bean
  public OrganizationType organizationType() {
    OrganizationTypeCreate organizationTypeCreate = new OrganizationTypeCreate();
    return organizationTypeService.createOrganizationType(organizationTypeCreate, securityContext);
  }

  @Configuration
  public static class UserConfig {
    @Bean
    @Qualifier("adminSecurityContext")
    public UserSecurityContext adminSecurityContext(AppUserService appUserService) {
      com.mirzet.zukic.runtime.model.AppUser admin =
          appUserService.createAppUser(
              new AppUserCreate().setUsername("admin@flexicore.com").setPassword("admin"), null);
      return new UserSecurityContext(admin);
    }
  }
}
