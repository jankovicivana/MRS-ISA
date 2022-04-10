Vue.component("cottage-profile",{
    template:`
         <section class="profile_main py-lg-3">
            <div class="container cottage_profile px-4 px-lg-5 my-5">
                <div class="row align-items-center pt-5">
                    <div class="col-md-6">
                        <img class="main_photo " src="../images/cottage4.jpg" alt="Cottage main photo" width="100%"/>
                        <div class="row thumbs pt-3 ">
                            <span class="side_photo col-3 px-1"><img src="../images/cottage1.jpg" alt="Cottage photo1" class="img-responsive" width="130px" height="130px"></span>
                            <span class="side_photo col-3 px-1" ><img src="../images/cottage3.jpg" alt="Cottage photo2" width="130px" height="130px"></span>
                            <span class="side_photo col-3 px-1" ><img src="../images/cottage5.jpg" alt="Cottage photo3" width="130px" height="130px"></span>
                            <span class="side_photo col-3 px-1"><img src="../images/cottage2.jpg" alt="Cottage photo4" width="130px" height="130px"></span>
                        </div>
                    </div>
                    <div class="col-md-6" >
                        <div class="row">
                            <div class="col-9 fw-bolder " style="font-size: 35px">Fensi vikendica</div>
                            <div class="col-3 ">Ocena 5 <i class="fas fa-star" style="color: black"></i></div>
                        </div>
                        
                        <div class="fs-5 m-3">
                            <span class="text-decoration-line-through">$45.00</span>
                            <span>$40.00</span>
                        </div>
                        <p class="lead p-3">Lorem ipsum dolor sit amet consectetur adipisicing elit. Praesentium at dolorem quidem modi. Nam sequi consequatur obcaecati excepturi alias magni, accusamus eius blanditiis delectus ipsam minima ea iste laborum vero?</p>
                        <div class="row number_info p-3">
                            <p class="col-2" > <i class="fas fa-user-friends"></i> 10</p>
                            <p class="col-2"><i class="fas fa-home"></i> 5</p>
                            <p class="col-2"><i class="fas fa-bed"></i> 2</p>
                        </div>
                        <div class="row p-3">
                            <p style="font-size: 25px">Dodatne usluge</p>
                            <div class="services">
                            <p><i class="fas fa-check-circle p-2"></i>Pet frendly</p>
                            <p><i class="fas fa-check-circle p-2"></i>Wi-fi</p>
                            <p><i class="fas fa-check-circle p-2"></i>Klimatizovano</p>
                            <p><i class="fas fa-check-circle p-2"></i>Parking</p>
                            <p><i class="fas fa-check-circle p-2"></i>Koktel vece</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row p-3">
                    <div class="col-4" style="background: #f8f2ec;border-radius: 5%">
                    <p style="font-size: 25px;">Pravila ponasanja</p>
                         <div class="rules">
                            <p><i class="fas fa-circle p-2"></i>Prijava posle 12h</p>
                            <p><i class="fas fa-circle p-2"></i>Odjava pre 17h</p>
                            <p><i class="fas fa-circle p-2"></i>Pusenje zabranjeno</p>
                            <p><i class="fas fa-circle p-2"></i>Ne lomiti stvari nikako</p>
                            <p><i class="fas fa-circle p-2"></i>Decu drzati podalje</p>
                         </div>
                    </div>
                    <div class="col-8" style="padding-left: 15px;">
                        <div class="px-3" style="background: #f8f2ec;border-radius: 5%;">
                            <p style="font-size: 25px;">Rezervacija</p>
                            <div class="pl-3">
                                Pocetni datum:
                                <input type="date" name="startDate" placeholder="dd-mm-yyyy">
                                Krajnji datum:
                                <input type="date" name="endDate" placeholder="dd-mm-yyyy">
                                Broj osoba:
                                <input type="number" name="numPeople" min="1" max="10" style="width: 50px">
                            </div>
                            <div class="res_button"><button type="button" class="btn ">Rezervisi</button></div>
                        </div>
                        
                    </div>
                </div>
                <div class="row ">
                    <div class="col-12" style="background: #f8f2ec;">
                        <p id="quick_heading">Brza rezervacija - jos malo pa nestalo!</p>
                        <div class="row p-3">
                            <div class="col-4 p-3 m-2 quick_res">
                                <div>
                                    <p class="res_date">24.05.2022 - 28.05.2022</p>
                                    <div class="discount">5%</div>
                                </div>
                                <p><i class="fas fa-user-friends"></i> 3</p>
                                <span class="text-decoration-line-through">$45.00</span>
                                <span class="before_price">$40.00</span>
                                <div class="quick_res_btn"><button type="button" class="btn">REZERVISI</button></div>
                            </div>
                            
                            <div class="col-4 p-3 m-2 quick_res">
                                <div>
                                    <p class="res_date">24.05.2022 - 28.05.2022</p>
                                    <div class="discount">5%</div>
                                </div>
                                <p><i class="fas fa-user-friends"></i> 3</p>
                                <span class="text-decoration-line-through">$45.00</span>
                                <span class="before_price">$40.00</span>
                                <div class="quick_res_btn"><button type="button" class="btn">REZERVISI</button></div>
                            </div>
                            
                            <div class="col-4 p-3 m-2 quick_res">
                                <div>
                                    <p class="res_date">24.05.2022 - 28.05.2022</p>
                                    <div class="discount">5%</div>
                                </div>
                                <p><i class="fas fa-user-friends"></i> 3</p>
                                <span class="text-decoration-line-through">$45.00</span>
                                <span class="before_price">$40.00</span>
                                <div class="quick_res_btn"><button type="button" class="btn">REZERVISI</button></div>
                            </div>
                            
                        </div>
                        
                    </div>
                </div>
            </div>
        </section>

    `
});