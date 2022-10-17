import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainComponent } from './component/main/main.component';
import { AdminRoutingModule } from './admin-routing.module';

@NgModule({
  declarations: [MainComponent],
  imports: [CommonModule, AdminRoutingModule],
})
export class AdminModule {}
