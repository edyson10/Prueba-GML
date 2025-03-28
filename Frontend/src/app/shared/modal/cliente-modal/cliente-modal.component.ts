import { Component, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ClienteService } from 'src/app/service/Cliente-service.service';

@Component({
  selector: 'app-cliente-modal',
  templateUrl: './cliente-modal.component.html',
  styleUrls: ['./cliente-modal.component.css']
})
export class ClienteModalComponent {
  
  @Output() closeModal = new EventEmitter<void>();
  @Output() saveClient = new EventEmitter<any>();

  clientForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  clients: any[] = [];

  title = "Crear nuevo cliente"

  constructor(private fb: FormBuilder, 
    private clienteService: ClienteService) {
    this.clientForm = this.fb.group({
      name: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });
  }

  onSubmit(): void {
    console.log("Hola")
    if (this.clientForm.valid) {
      this.saveClient.emit(this.clientForm.value);
      //this.closeModal.emit();
      console.log(this.clientForm.value)
    }
  }

  exportToCsv(): void {
    // Filtrar solo los datos necesarios si es necesario
    const dataToExport = this.clients.map(client => ({
      'Shared Key': client.sharedKey,
      'Business ID': client.businessId,
      'Email': client.email,
      'Phone': client.phone,
      'Data Added': client.dataAdded
    }));
    
    this.clienteService.exportToCsv('clientes.csv', dataToExport);
  }
}
