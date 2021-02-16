import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, ValidatorFn, Validators} from '@angular/forms';
import {Computation} from '../../model/computation.model';
import {ComputeService} from '../../service/compute.service';
import {OperationType} from '../../model/operation-type.enum';
import {Store} from '@ngxs/store';
import {AddTerms, DivideTerms, MultiplyTerms, SubstractTerms} from '../../action/calculator.actions';
import {ErrorStateMatcher} from '@angular/material/core';

/** Error when invalid control is dirty, touched, or submitted. */
export class FormControlStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const controlSubmitted = form && form.submitted;
    return control && control.invalid && (control.dirty || control.touched || controlSubmitted);
  }
}

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  formGroup: FormGroup;
  divisionProhibited;
  OperationType = OperationType;
  operations: {name: OperationType, symbol: string}[] = [];
  matcher = new FormControlStateMatcher();

  constructor(private readonly formBuilder: FormBuilder,
              private readonly computeService: ComputeService,
              private readonly store: Store) {
  }

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group({
      firstTerm: this.formBuilder.control(null, [Validators.required, this.isNumber()]),
      secondTerm: this.formBuilder.control(null, [Validators.required, this.isNumber()]),
    });

    this.formGroup.valueChanges.subscribe((params: Computation) => {
      this.divisionProhibited = Number(params.secondTerm) === Number(0) ? true : false;
    });

    this.initOperations();
  }

  isNumber(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const regexNumber: RegExp = new RegExp('^[+|-]?\\d+\\.?[\\d]*$');
      if (control.value) {
        return regexNumber.test(control.value) ? null : {isNotNumber: true};
      } else {
        return null;
      }
    };
  }

  private initOperations(): void {
    this.operations.push({name: OperationType.ADDITION, symbol: '+'});
    this.operations.push({name: OperationType.SUBSTRACTION, symbol: '-'});
    this.operations.push({name: OperationType.MULTIPLICATION, symbol: '*'});
    this.operations.push({name: OperationType.DIVISION, symbol: '/'});
  }

  compute(operation: OperationType): void {
    const computationParams: Computation = this.formGroup.getRawValue();
    let calculatorAction;
    switch (operation) {
      case OperationType.ADDITION: {
        calculatorAction = new AddTerms(computationParams.firstTerm, computationParams.secondTerm);
        break;
      }
      case OperationType.SUBSTRACTION: {
        calculatorAction = new SubstractTerms(computationParams.firstTerm, computationParams.secondTerm);
        break;
      }
      case OperationType.MULTIPLICATION: {
        calculatorAction = new MultiplyTerms(computationParams.firstTerm, computationParams.secondTerm);
        break;
      }
      case OperationType.DIVISION: {
        calculatorAction = new DivideTerms(computationParams.firstTerm, computationParams.secondTerm);
        break;
      }
      default: {
        console.error('Unsupported operation !');
      }
    }
    if (calculatorAction) {
      this.store.dispatch(calculatorAction);
    }
  }

}
