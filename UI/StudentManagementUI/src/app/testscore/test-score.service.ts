import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TestScoreService {

  private baseUrl = 'http://localhost:8080/scores';
  private studentUrl = 'http://localhost:8080/students/';

  constructor(private http: HttpClient) { }

  getScoreByIdStudent(id: number): Observable<any> {
    return this.http.get(`${this.studentUrl}/${id}/scores/`);
  }

  getScoreByIdClassAndIdSubject(id_class: number, id_subject: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id_class}/${id_subject}`);
  }  

}