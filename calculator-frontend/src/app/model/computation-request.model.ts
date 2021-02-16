import {OperationType} from './operation-type.enum';

export interface ComputationRequest {
  firstTerm: number;
  secondTerm: number;
  operationType: OperationType;
}
