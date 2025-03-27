import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../core/model/Cliente';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  private apiUrl = 'http://localhost:8080/api/v1/clientes';

  constructor(private http: HttpClient) { }

  getClients(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/listar`);
  }
}
