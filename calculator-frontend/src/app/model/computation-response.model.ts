import {ComputationRequest} from './computation-request.model';
import {ValidationInfo} from './validation-info.model';

export interface ComputationResponse {
  request: ComputationRequest;
  validation: ValidationInfo;
  result: number;
}
