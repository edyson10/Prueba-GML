import { Component, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-cliente-modal',
  templateUrl: './cliente-modal.component.html',
  styleUrls: ['./cliente-modal.component.css']
})
export class ClienteModalComponent {
  
  @Output() closeModal = new EventEmitter<void>();
  @Output() saveClient = new EventEmitter<any>();

  clientForm: FormGroup;

  title = "Crear nuevo cliente"

  constructor(private fb: FormBuilder) {
    this.clientForm = this.fb.group({
      name: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });
  }
}
