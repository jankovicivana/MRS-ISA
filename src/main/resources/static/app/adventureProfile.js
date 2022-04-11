Vue.component("adventure-profile",{
    template:  `

<section class="profile_main py-lg-3" style="background-image: url('https://images.unsplash.com/photo-1551606292-9649254815d6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=888&q=80');background-size: 100% 100%; ">
    <div class="container cottage_profile px-4 px-lg-5 my-5" >
        <div class="row align-items-center pt-5">
            <div class="col-md-6">
                <img class="main_photo " src="../images/pic4.jpg" alt="Cottage main photo" width="100%"/>
                <div class="row thumbs pt-3 ">
                    <span class="side_photo col-3 px-1"><img src="../images/pic1.jpg" alt="Cottage photo1"
                                                                 class="img-responsive" width="130px"
                                                                 height="130px"></span>
                    <span class="side_photo col-3 px-1"><img src="../images/pic2.jpg" alt="Cottage photo2"
                                                                 width="130px" height="130px"></span>
                    <span class="side_photo col-3 px-1"><img src="../images/pic3.jpg" alt="Cottage photo3"
                                                                 width="130px" height="130px"></span>
                    <span class="side_photo col-3 px-1"><img src="../images/pic5.jpg" alt="Cottage photo4"
                                                                 width="130px" height="130px"></span>
                </div>
            </div>
            <div class="col-md-6"  >
                <div class="row">
                    <div class="col-9 fw-bolder " style="font-size: 35px">Fishing adventure in wilderness</div>
                    <div class="col-3 ">Grade 5 <i class="fas fa-star" style="color: black"></i></div>
                </div>

                <div class="fs-5 m-3">
                    <span class="text-decoration-line-through">$45.00</span>
                    <span>$40.00</span>
                </div>
                <p class="lead p-1">Fishing is the activity of trying to catch fish. Fish are often caught
                 in the wild but may also be caught from stocked bodies of water. 
                 Techniques for catching fish include hand gathering, spearing, netting, 
                 angling and trapping.</p>
                <div class="row number_info  p-1">
                    <p class="col-2"> <i class="fas fa-user-friends"></i>  10</p>
                    <p class="col-2" ><i class="fas fa-percentage"></i> 5<span style="font-size: xx-small">(cancel fee)</span> </p>
                </div>

                <div class="row p-1">
                    <p style="font-size: 25px">Additional services</p>
                    <div class="services">
                        <p><i class="fas fa-check-circle p-2"></i>Additional boat</p>
                        <p><i class="fas fa-check-circle p-2"></i>Wi-fi</p>
                        <p><i class="fas fa-check-circle p-2"></i>Parking</p>
                        <p><i class="fas fa-check-circle p-2"></i>Free hooks</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row p-3">
            <div class="col-4" style="background: #f8f2ec;border-radius: 5%">
                <p style="font-size: 25px;">Rules</p>
                <div class="rules">
                    <p><i class="fas fa-circle p-2"></i>Mustn't jump in water</p>
                    <p><i class="fas fa-circle p-2"></i>Must be older than 18</p>
                    </div>
            </div>
            <div class="col-8" style="padding-left: 15px;">
                <div class="px-3" style="background: #f8f2ec;border-radius: 5%;">
                    <p style="font-size: 25px;">Reservations:</p>
                    <div class="pl-3">
                        Start date:
                        <input type="date" name="startDate" placeholder="dd-mm-yyyy">
                            End date:
                            <input type="date" name="endDate" placeholder="dd-mm-yyyy">
                                Person number:
                                <input type="number" name="numPeople" min="1" max="10" style="width: 50px">
                    </div>
                    <div class="res_button">
                        <button type="button" class="btn ">Reserve</button>
                    </div>
                </div>

            </div>
        </div>
        <hr/>
        <div class="row ">
            <div class="row col-12" style="background: #f8f2ec;">
                <h3>Fishing instructor</h3>
                <div class="row p-3">
                    <div class="col-6  ">
                        <span class="side_photo p-lg-3 px-1"><img src="../images/pera2.jpg" alt="Cottage photo4"
                                          width="330px"                       ></span>
                    </div>

                       <br/>

                    <div class=" p-3 m-2 ">
                        <h5>Short biography</h5>

                        Ken's coldwater and warmwater fishing experience has been chasing a variety of species with
                         a fly rod including Trout, Landlocked Atlantic Salmon, Smallmouth Bass,Striped Bass, and 
                         Fall-run Great Lakes Steelhead and Brown Trout. Regional trips have included fly fishing 
                         in the Southeast, the Northeast and the Rocky Mountain states. He ties many of his own 
                         flies and owns WAY too many fly tying materials. Locally, during the Winter months, 
                         Ken actively pursues Striped Bass and KY Spotted Bass on Lake Sidney Lanier.
                    </div>

                    

                </div>

            </div>
        </div>
        <hr/>
        <div class="row ">
            <div class="col-12" style="background: #f8f2ec;">
                <p id="quick_heading">Quick reservations - enormous discounts!</p>
                <div class="row p-3">
                    <div class="col-4 p-3 m-2 quick_res">
                        <div>
                            <p class="res_date">24.05.2022 - 28.05.2022</p>
                            <div class="discount">5%</div>
                        </div>
                        <p><i class="fas fa-user-friends"></i> 3</p>
                        <span class="text-decoration-line-through">$45.00</span>
                        <span class="before_price">$40.00</span>
                        <div class="quick_res_btn">
                            <button type="button" class="btn">RESERVE</button>
                        </div>
                    </div>

                    <div class="col-4 p-3 m-2 quick_res">
                        <div>
                            <p class="res_date">24.05.2022 - 28.05.2022</p>
                            <div class="discount">5%</div>
                        </div>
                        <p><i class="fas fa-user-friends"></i> 3</p>
                        <span class="text-decoration-line-through">$45.00</span>
                        <span class="before_price">$40.00</span>
                        <div class="quick_res_btn">
                            <button type="button" class="btn">RESERVE</button>
                        </div>
                    </div>

                    <div class="col-4 p-3 m-2 quick_res">
                        <div>
                            <p class="res_date">24.05.2022 - 28.05.2022</p>
                            <div class="discount">5%</div>
                        </div>
                        <p><i class="fas fa-user-friends"></i> 3</p>
                        <span class="text-decoration-line-through">$45.00</span>
                        <span class="before_price">$40.00</span>
                        <div class="quick_res_btn">
                            <button type="button" class="btn">RESERVE</button>
                        </div>
                    </div>

                </div>

            </div>
        </div>
    </div>
</section>
        `
});
