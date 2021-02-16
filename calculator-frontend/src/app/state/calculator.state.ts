import {Computation} from '../model/computation.model';
import {Action, Selector, State, StateContext} from '@ngxs/store';
import {Injectable} from '@angular/core';
import {ComputeService} from '../service/compute.service';
import {OperationType} from '../model/operation-type.enum';
import {tap} from 'rxjs/operators';
import {ComputationResponse} from '../model/computation-response.model';
import {Observable} from 'rxjs';
import {AddTerms, DivideTerms, MultiplyTerms, SubstractTerms} from '../action/calculator.actions';
import {ComputationRequest} from '../model/computation-request.model';

export class CalculatorStateModel {
  params: Computation;
  result: number;
}

@State<CalculatorStateModel>({
  name: 'computation',
  defaults: {
    params: null,
    result: null
  }
})

@Injectable()
export class CalculatorState {

  constructor(private readonly computeService: ComputeService) {
  }

  @Selector()
  static getParams(state: CalculatorStateModel): Computation {
    return state.params;
  }

  @Selector()
  static getValue(state: CalculatorStateModel): number {
    return state.result;
  }

  @Action(AddTerms)
  addition({getState, patchState}: StateContext<CalculatorStateModel>,
           {firstTerm, secondTerm}: AddTerms): Observable<ComputationResponse> {
    return this.patchState({firstTerm, secondTerm, operationType: OperationType.ADDITION}, patchState);
  }

  @Action(SubstractTerms)
  substraction({getState, patchState}: StateContext<CalculatorStateModel>,
               {firstTerm, secondTerm}: SubstractTerms): Observable<ComputationResponse> {
    return this.patchState({firstTerm, secondTerm, operationType: OperationType.SUBSTRACTION}, patchState);
  }

  @Action(MultiplyTerms)
  multiplication({getState, patchState}: StateContext<CalculatorStateModel>,
                 {firstTerm, secondTerm}: MultiplyTerms): Observable<ComputationResponse> {
    return this.patchState({firstTerm, secondTerm, operationType: OperationType.MULTIPLICATION}, patchState);
  }

  @Action(DivideTerms)
  division({getState, patchState}: StateContext<CalculatorStateModel>,
           {firstTerm, secondTerm}: DivideTerms): Observable<ComputationResponse> {
    return this.patchState({firstTerm, secondTerm, operationType: OperationType.DIVISION}, patchState);
  }

  private patchState(computationRequest: ComputationRequest, patchState): Observable<ComputationResponse> {
    return this.computeService.evaluate(computationRequest).pipe(tap((response: ComputationResponse) => {
      patchState({
        params: {... computationRequest},
        result: response.result
      });
    }));
  }

}
