import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuariosFormulariosComponent } from './usuarios-formularios.component';

describe('UsuariosFormulariosComponent', () => {
  let component: UsuariosFormulariosComponent;
  let fixture: ComponentFixture<UsuariosFormulariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsuariosFormulariosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsuariosFormulariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
