
package ftn.mrs.isa.rentalapp.config;

import ftn.mrs.isa.rentalapp.security.RestAuthenticationEntryPoint;
import ftn.mrs.isa.rentalapp.security.TokenAuthenticationFilter;
import ftn.mrs.isa.rentalapp.service.UserService;
import ftn.mrs.isa.rentalapp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
// Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
    // BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Servis koji se koristi za citanje podataka o korisnicima aplikacije
    @Autowired
    private UserService userService;

    // Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    // Registrujemo authentication manager koji ce da uradi autentifikaciju korisnika za nas
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Definisemo nacin utvrdjivanja korisnika pri autentifikaciji
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // Definisemo uputstva AuthenticationManager-u:

                // 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
                // prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
                .userDetailsService(userService)

                // 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu
                // da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
                .passwordEncoder(this.passwordEncoder());
    }

    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
    @Autowired
    private TokenUtils tokenUtils;

    // Definisemo prava pristupa za zahteve ka odredjenim URL-ovima/rutama
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                // svim korisnicima dopusti da pristupe sledecim putanjama:
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()		// /auth/**
                .antMatchers("/api/cottages/all").permitAll()
                .antMatchers("/api/cottages/{id}").permitAll()
                .antMatchers("/api/boats/all").permitAll()
                .antMatchers("/api/boats/{id}").permitAll()
                .antMatchers("/api/adventures/all").permitAll()
                .antMatchers("/api/adventures/{id}").permitAll()
                .antMatchers("/api/client/all").permitAll()
                .antMatchers("/api/address/all").permitAll()
                .antMatchers("/api/availablePeriod/getAvailablePeriod/{id}").permitAll()
                .antMatchers("/api/reservation/findAllOfCottage/{id}").permitAll()
                .antMatchers("/api/quickReservation/findQuickReservationBy/{id}").permitAll()
                .antMatchers("/api/reservation/findAllOfBoat/{id}").permitAll()


                // za svaki drugi zahtev korisnik mora biti autentifikovan
                .anyRequest().authenticated().and()

                .logout(logout -> logout.logoutUrl("/").logoutSuccessUrl("/").invalidateHttpSession(true))

                // za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
                .cors().and()

                // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userService), BasicAuthenticationFilter.class);

        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        // Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/register");

        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }

}
