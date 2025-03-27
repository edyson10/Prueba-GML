import { Component } from '@angular/core';
import { Cliente } from '../core/model/Cliente';
import { ClienteService } from '../service/Cliente-service.service';

@Component({
  selector: 'app-listar-cliente',
  templateUrl: './listar-cliente.component.html',
  styleUrls: ['./listar-cliente.component.css']
})
export class ListarClienteComponent {

  clients: Cliente[] = [];

  constructor(private clienteService: ClienteService) { }

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.clienteService.getClients().subscribe(
      data =>  {
        this.clients = data 
        console.log(this.clients)
      },
      error => console.error('Error loading clients', error)
    );
  }

}
