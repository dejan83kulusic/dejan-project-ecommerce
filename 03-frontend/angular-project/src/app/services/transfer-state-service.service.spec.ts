import { TestBed } from '@angular/core/testing';

import { TransferStateServiceService } from './transfer-state-service.service';

describe('TransferStateServiceService', () => {
  let service: TransferStateServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransferStateServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
