package com.mirzet.zukic.runtime.service;

import com.mirzet.zukic.runtime.data.ClientRepository;
import com.mirzet.zukic.runtime.model.Client;
import com.mirzet.zukic.runtime.request.ClientCreate;
import com.mirzet.zukic.runtime.request.ClientFilter;
import com.mirzet.zukic.runtime.request.ClientUpdate;
import com.mirzet.zukic.runtime.response.PaginationResponse;
import com.mirzet.zukic.runtime.security.UserSecurityContext;
import jakarta.persistence.metamodel.SingularAttribute;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientService {

  @Autowired private ClientRepository repository;

  @Autowired private BasicService basicService;

  /**
   * @param clientCreate Object Used to Create Client
   * @param securityContext
   * @return created Client
   */
  public Client createClient(ClientCreate clientCreate, UserSecurityContext securityContext) {
    Client client = createClientNoMerge(clientCreate, securityContext);
    this.repository.merge(client);
    return client;
  }

  /**
   * @param clientCreate Object Used to Create Client
   * @param securityContext
   * @return created Client unmerged
   */
  public Client createClientNoMerge(
      ClientCreate clientCreate, UserSecurityContext securityContext) {
    Client client = new Client();
    client.setId(UUID.randomUUID().toString());
    updateClientNoMerge(client, clientCreate);

    return client;
  }

  /**
   * @param clientCreate Object Used to Create Client
   * @param client
   * @return if client was updated
   */
  public boolean updateClientNoMerge(Client client, ClientCreate clientCreate) {
    boolean update = basicService.updateBasicNoMerge(client, clientCreate);

    return update;
  }

  /**
   * @param clientUpdate
   * @param securityContext
   * @return client
   */
  public Client updateClient(ClientUpdate clientUpdate, UserSecurityContext securityContext) {
    Client client = clientUpdate.getClient();
    if (updateClientNoMerge(client, clientUpdate)) {
      this.repository.merge(client);
    }
    return client;
  }

  /**
   * @param clientFilter Object Used to List Client
   * @param securityContext
   * @return PaginationResponse<Client> containing paging information for Client
   */
  public PaginationResponse<Client> getAllClients(
      ClientFilter clientFilter, UserSecurityContext securityContext) {
    List<Client> list = listAllClients(clientFilter, securityContext);
    long count = this.repository.countAllClients(clientFilter, securityContext);
    return new PaginationResponse<>(list, clientFilter.getPageSize(), count);
  }

  /**
   * @param clientFilter Object Used to List Client
   * @param securityContext
   * @return List of Client
   */
  public List<Client> listAllClients(
      ClientFilter clientFilter, UserSecurityContext securityContext) {
    return this.repository.listAllClients(clientFilter, securityContext);
  }

  public <T, I> List<T> listByIds(Class<T> c, SingularAttribute<T, I> idField, Set<I> ids) {
    return repository.listByIds(c, idField, ids);
  }

  public <T, I> T getByIdOrNull(Class<T> c, SingularAttribute<T, I> idField, I id) {
    return repository.getByIdOrNull(c, idField, id);
  }

  public void merge(java.lang.Object base) {
    this.repository.merge(base);
  }

  public void massMerge(List<?> toMerge) {
    this.repository.massMerge(toMerge);
  }
}
