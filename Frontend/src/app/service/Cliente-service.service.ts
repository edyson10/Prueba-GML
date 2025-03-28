import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Cliente } from '../core/model/Cliente';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {

  private apiUrl = 'http://localhost:8080/api/v1/clientes';

  constructor(private http: HttpClient) { }

  /**
   * Servicio para listar todos los clientes
   * @returns Lista con todos los clientes registrados
   */
  getClients(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/listar`);
  }

  /**
   * Servicio para crea un nuevo cliente
   * @param clienteData Información del cliente a registrar
   * @returns Usuario creado
   */
  createClient(clienteData: any): Observable<any> {
    
    const cliente = {
      businessId: clienteData.name,
      email: clienteData.email,
      phone: clienteData.phone,
      startDate: clienteData.startDate,
      endDate: clienteData.endDate
    };

    return this.http.post(`${this.apiUrl}/crear`, cliente).pipe(
      catchError(this.handleError)
    );
  }

  exportToCsv(filename: string, rows: Record<string, string | number>[]): void {
    if (!rows || !rows.length) {
      return;
    }

    const separator = ',';
    const keys = Object.keys(rows[0]);
    const csvContent =
      keys.join(separator) +
      '\n' +
      rows.map(row => {
        return keys.map(k => {
          let cell = row[k] === null || row[k] === undefined ? '' : row[k];
          // Escapar comillas dobles
          cell = cell.toString().replace(/"/g, '""');
          // Si contiene comas, saltos de línea o comillas, envolver en comillas
          if (cell.search(/("|,|\n)/g) >= 0) {
            cell = `"${cell}"`;
          }
          return cell;
        }).join(separator);
      }).join('\n');

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    
    if (link.download !== undefined) {
      // Generar URL para el blob
      const url = URL.createObjectURL(blob);
      
      // Configurar el enlace de descarga
      link.setAttribute('href', url);
      link.setAttribute('download', filename);
      link.style.visibility = 'hidden';
      
      // Añadir al DOM, hacer click y remover
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  }

  private handleError(error: any): Observable<never> {
    let errorMessage = 'Error al crear el cliente';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Código: ${error.status}\n Mensaje: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}
