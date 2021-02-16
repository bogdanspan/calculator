import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ComputationResponse} from '../model/computation-response.model';
import {ComputationRequest} from '../model/computation-request.model';

@Injectable()
export class ComputeService {

  constructor(private readonly httpClient: HttpClient) {
  }

  evaluate(computationRequest: ComputationRequest): Observable<ComputationResponse> {
    return this.httpClient.get<ComputationResponse>(`/api/operation/${computationRequest.operationType.toString()
      .toLowerCase()}/${computationRequest.firstTerm}/${computationRequest.secondTerm}`);
  }

}
