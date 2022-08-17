import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { AuthState, OktaAuth } from '@okta/okta-auth-js';
import { filter, map, Observable } from 'rxjs';
@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {
isAuthenticated$: boolean = false;
userFullName!: string;
 constructor(private _router: Router, private _oktaStateService: OktaAuthStateService, @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth) { }

 public ngOnInit(): void {
  this._oktaStateService.authState$.subscribe(
    (result: any) => {
      this.isAuthenticated$ = result;
      this.getUserDetails();
    }
  );

}

getUserDetails() {
  if (this.isAuthenticated$) {

    // Fetch the logged in user details (user's claims)
    //
    // user full name is exposed as a property name
    this._oktaAuth.getUser().then(
      (res: any) => {
        this.userFullName = res.name;

        // retrieve the user's email from authentication response
        const theEmail = res.email;

        // now store the email in browser storage
      }
    );
  }
}
 public async signIn() : Promise<void> {
 await this._oktaAuth.signInWithRedirect().then(
 _ => this._router.navigate(['/login'])
);
 }

public async signout(): Promise<void> {
 await this._oktaAuth.signOut();
}
}

