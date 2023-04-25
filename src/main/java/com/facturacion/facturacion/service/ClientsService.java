package com.facturacion.facturacion.service;

import com.facturacion.facturacion.entities.Clients;
import com.facturacion.facturacion.entities.Invoice;
import com.facturacion.facturacion.exception.ClientsAlreadyExistException;
import com.facturacion.facturacion.exception.ClientsNotFoundException;
import com.facturacion.facturacion.repository.ClientsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClientsService {

    @Autowired
    private ClientsRepository clientsRepository;

    public Clients create(Clients newCliente) throws ClientsAlreadyExistException {
        if (newCliente.getNombre().isEmpty() || newCliente.getLastname().isEmpty() || newCliente.getNumerodocumento().isEmpty()) {
            log.info("Uno o más campos de cliente están vacíos");
            throw new IllegalArgumentException("Uno o más campos de cliente están vacíos");
        }
        Optional<Clients> clienteOp = this.clientsRepository.findByNumerodocumento(newCliente.getNumerodocumento());
        if (clienteOp.isPresent()){
            log.info("El cliente que intenta agregar ya existe en la base de datos : " + newCliente);
            throw new ClientsAlreadyExistException("El cliente que intenta agregar ya existe en la BDD");
        }else {
            return this.clientsRepository.save(newCliente);
        }
    }

    public Clients update(Clients newClients, Long id) throws Exception {
        log.info("ID INGRESADO : " + id);
        if (id <= 0){
            throw new Exception("El id brindado no es valido");
        }

        Optional<Clients> clientOp = this.clientsRepository.findById(id);

        if (clientOp.isEmpty()){
            log.info("El cliente que intenta modificar no existe en la base de datos : " + newClients);
            throw new ClientsNotFoundException("El cliente que intenta modificar no existe en la base de datos");
        }else {
            log.info("el cliente fue encontrado");
            Clients clienteBd = clientOp.get();

            //validamos que el id de la uri y el id del JSON sean iguales para poder modificarlos
            if(clientOp.get().getId()==newClients.getId()) {
                clienteBd.setId(newClients.getId());
                clienteBd.setNombre(newClients.getNombre());
                clienteBd.setLastname(newClients.getLastname());
                log.info("cliente actualizado : " + clienteBd);
            }
            else{
                log.info("No se puede modificar un identificador");
            }

            boolean existe;
            existe = existsByNumerodocumento(newClients.getNumerodocumento());
            //validar
            if(existe)
                log.info("El número de documento que intenta actualizar ya lo posee otro cliente");
            else{
                clienteBd.setNumerodocumento(newClients.getNumerodocumento());
            }

            return this.clientsRepository.save(clienteBd);
        }
    }

    /* Devuelve un booleano indicando si existe o no un cliente con el número de documento buscado en la base de datos.*/
    public boolean existsByNumerodocumento(String docnumber) {
        return this.clientsRepository.existsByNumerodocumento(docnumber);
    }

    public Clients findById(Long id) throws Exception {
        if (id <= 0){
            throw new Exception("El id brindado no es valido.");
        }

        Optional<Clients> clientOp = this.clientsRepository.findById(id);

        if (clientOp.isEmpty()){
            log.info("El cliente con el id brindado no existe en la base de datos : " + id);
            throw new ClientsNotFoundException("El cliente que intenta solicitar no existe");
        }else {
            Clients client = clientOp.get();
            String numeroDocumento = client.getNumerodocumento();
            String apellido = client.getLastname();
            String nombre = client.getNombre();
            return new Clients(id, nombre , apellido, numeroDocumento);
           //return clientOp.get();
        }
    }
    
    public List<Clients> findAll(){
        return this.clientsRepository.findAll();
    }

}
