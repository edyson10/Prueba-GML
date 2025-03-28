import { Component, ViewChild } from '@angular/core';
import { ClienteService } from '../service/Cliente-service.service';
import { ListarClienteComponent } from '../listar-cliente/listar-cliente.component';

@Component({
  selector: 'app-crear-cliente',
  templateUrl: './crear-cliente.component.html',
  styleUrls: ['./crear-cliente.component.css']
})
export class CrearClienteComponent {

  @ViewChild(ListarClienteComponent) listarClienteComponent!: ListarClienteComponent;
  showModal = false;
  
  constructor(private clienteService: ClienteService) { }
  
  openModal(): void {
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
  }

  onSaveClient(cliente: any): void {
    // Lógica para guardar el cliente
    console.log('Cliente a guardar:', cliente);
    
      this.clienteService.createClient(cliente).subscribe(
        data =>  {
          console.log(data)
        },
        error => console.error('Error al crear un cliente.', error)
      );
    this.closeModal();
    window.location.reload();
  }

  exportToCsv(): void {
    if (!this.listarClienteComponent) {
      console.error('Componente hijo no disponible');
      return;
    }

    const exportData = this.listarClienteComponent.getClientsForExport();
    if (exportData.length > 0) {
      this.downloadCsv(exportData, 'clientes_exportados.csv');
    } else {
      alert('No hay datos para exportar');
    }
  }

  private downloadCsv(data: any[], filename: string): void {
    const csvContent = this.convertToCsv(data);
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    
    if (link.download !== undefined) {
      const url = URL.createObjectURL(blob);
      link.setAttribute('href', url);
      link.setAttribute('download', filename);
      link.style.visibility = 'hidden';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }

  private convertToCsv(data: any[]): string {
    const separator = ',';
    const keys = Object.keys(data[0]);
    
    return [
      keys.join(separator),
      ...data.map(row => 
        keys.map(k => 
          `"${row[k] === null || row[k] === undefined ? '' : row[k].toString().replace(/"/g, '""')}"`
        ).join(separator)
      )
    ].join('\n');
  }

}
