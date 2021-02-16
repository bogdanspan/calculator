import {Computation} from '../model/computation.model';

export class AddTerms {
  static readonly type = '[CALCULATOR] Addition';

  constructor(public firstTerm: number, public secondTerm: number) {}

}

export class SubstractTerms {
  static readonly type = '[CALCULATOR] Subtraction';

  constructor(public firstTerm: number, public secondTerm: number) {}

}

export class MultiplyTerms {
  static readonly type = '[CALCULATOR] Multiplication';

  constructor(public firstTerm: number, public secondTerm: number) {}

}

export class DivideTerms {
  static readonly type = '[CALCULATOR] Division';

  constructor(public firstTerm: number, public secondTerm: number) {}

}
