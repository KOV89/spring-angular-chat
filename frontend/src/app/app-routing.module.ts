import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './components/main/main.component';
import { AppGuard } from './app.guard';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  { path: '', component: MainComponent, canActivate: [AppGuard] },
  { path: 'login', component: LoginComponent, canActivate: [AppGuard] },
  {
    path: 'admin',
    canActivate: [AppGuard],
    canLoad: [AppGuard],
    children: [
      {
        path: '',
        loadChildren: () => import('../modules/admin/admin.module').then((m) => m.AdminModule),
      },
    ],
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
