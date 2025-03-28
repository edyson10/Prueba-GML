import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ClienteService } from './Cliente-service.service';
import { Cliente } from '../core/model/Cliente';

describe('ClientService', () => {
  let service: ClienteService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ClienteService]
    });
    service = TestBed.inject(ClienteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve clients', () => {
    const mockClients: Cliente[] = [
      { sharedKey: 'jguiterrez', businessId: 'Juliana Gutierrez', email: 'jguiterrez@gmail.com', phone: '3219876543', startDate: new Date(), endDate: new Date(), dateAdded: new Date() }
    ];

    service.getClients().subscribe(clients => {
      expect(clients.length).toBe(1);
      expect(clients).toEqual(mockClients);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/clientes/listar');
    expect(req.request.method).toBe('GET');
    req.flush(mockClients);
  });

  it('should create a client', () => {
    const nuevoCliente: Cliente = { sharedKey: '', businessId: 'Test User', email: 'test@test.com', phone: '1234567890', dateAdded: new Date(), startDate: new Date(), endDate: new Date() };

    service.createClient(nuevoCliente).subscribe(cliente => {
      expect(cliente).toBeTruthy();
    });

    const req = httpMock.expectOne('http://localhost:8080/api/v1/clientes/crear');
    expect(req.request.method).toBe('POST');
    req.flush({ ...nuevoCliente, sharedKey: 'tuser', dateAdded: new Date() });
  });
});