import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class ReferenceRestService {

  constructor(
    private http: HttpClient
  ) {}

  getAllProjectReferences() : Observable<Array<ValueDto>> {
    return this.http.get<Array<ValueDto>>('http://localhost:8080/api/project/all');
  }

  getAllPrioritiesReferences() : Observable<Array<string>> {
    return this.http.get<Array<string>>('http://localhost:8080/api/reference/priorities');
  }

  getAllUserReferences(): Observable<Array<ValueDto>> {
    return this.http.get<Array<ValueDto>>('http://localhost:8080/api/user/all');
  }

}

export interface ValueDto {
  id: number,
  name: string

}
