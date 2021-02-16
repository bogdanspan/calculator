import {OperationType} from './operation-type.enum';

export interface Computation {
  firstTerm: number;
  secondTerm: number;
  operationType?: OperationType;
}
