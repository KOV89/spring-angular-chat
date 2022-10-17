import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
/**
 * Абстрактный сервис реализующий crud операции с объектом
 *
 * @param <E>  entity объекта
 * @param <ID> id entity
 */

export abstract class CrudService<E extends { id: I | undefined }, I = number> {
  protected baseUrl = '/api';
  protected abstract serviceUrl: string;
  protected abstract http: HttpClient;

  getById(id: I): Observable<E> {
    return this.http.get<E>(`${this.baseUrl + this.serviceUrl}/${id}`);
  }

  getAllByPage(page?: number, size?: number, field?: keyof E, direction?: 'asc' | 'desc'): Observable<Page<E>> {
    let params = new HttpParams();
    if (page || page === 0) params = params.append('page', page);
    if (size) params = params.append('size', size);
    if (field) params = params.append('field', field.toString());
    if (direction) params = params.append('direction', direction);
    return this.http.get<Page<E>>(`${this.baseUrl + this.serviceUrl}`, { params });
  }

  create(object: Omit<E, 'id'>): Observable<E> {
    return this.http.post<E>(`${this.baseUrl + this.serviceUrl}`, object);
  }

  update(object: E): Observable<E> {
    return this.http.put<E>(`${this.baseUrl + this.serviceUrl}`, object);
  }

  deleteById(id: I) {
    return this.http.delete(`${this.baseUrl + this.serviceUrl}/${id}`);
  }
}
