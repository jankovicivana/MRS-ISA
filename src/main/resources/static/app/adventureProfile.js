Vue.component("adventure-profile",{
    data: function (){
        return{
            adventure: ''
        }
    },
    template:  `

<section class="profile_main py-lg-3" style="background-image: url('https://images.unsplash.com/photo-1551606292-9649254815d6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=888&q=80');background-size: 100% 100%; ">
    <div class="container cottage_profile px-4 px-lg-5 my-5" >
        <div class="row align-items-center pt-5">
            <div class="col-md-6">
                <img class="main_photo " src="../images/pic4.jpg" alt="Cottage main photo" width="100%"/>
                <div class="row thumbs pt-3 ">
                    <span v-for="i in adventure.images" class="side_photo col-3 px-1" style="padding-top: 10px;"><img :src="i.path" alt="Cottage photo1" class="img-responsive" width="130px" height="130px"></span>
                </div>
            </div>
            <div class="col-md-6"  >
                <div class="row">
                    <div class="col-9 fw-bolder " style="font-size: 35px">{{adventure.name}}</div>
                    <div class="col-3 ">Grade 5 <i class="fas fa-star" style="color: black"></i></div>
                </div>

                <div class="fs-5 m-3">
                    <span class="text-decoration-line-through">$45.00</span>
                    <span>$40.00</span>
                </div>
                <p class="lead p-1">{{adventure.description}}</p>
                <div class="row number_info  p-1">
                    <p class="col-2"> <i class="fas fa-user-friends"></i>  {{adventure.maxPersonNum}}</p>
                    <p class="col-2" ><i class="fas fa-percentage"></i> {{adventure.cancelFee}}<span style="font-size: xx-small">(cancel fee)</span> </p>
                </div>

                <div class="row p-1">
                    <p style="font-size: 25px">Additional services</p>
                    <div class="services">
                        <p v-for="as in adventure.additionalServices"><i class="fas fa-check-circle p-2"></i>{{as.name}}</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row p-3">
            <div class="col-5" style="background: #f8f2ec;border-radius: 5%">
                <p style="font-size: 25px;">Rules</p>
                <div class="rules">
                    <p v-for="r in adventure.rules"><i class="fas fa-circle p-2"></i>{{r.rule}}</p>
                </div>
            </div>
            
            <div class="col-5" style="background: #f8f2ec;border-radius: 5%;margin-left: 95px;padding-left: 25px">
                <p style="font-size: 25px;">Fishing equipment</p>
                <div class="rules">
                    <p v-for="r in adventure.fishingEquipment"><i class="fas fa-circle p-2"></i>{{r.equipment}}</p>
                </div>
            </div>
            
            
        </div>
        <hr/>
        <div class="row col-12" style="alignment: center;">
            <div class="px-3" style="background: #f8f2ec; ">
                <h3>Fishing instructor</h3>
                <div class="row p-3">
                    <div class="col-6  ">
                        <span class="side_photo p-lg-3 px-1"><img src="../images/pera2.jpg" alt="Cottage photo4"
                                          width="330px"                       ></span>
                    </div>

                    <br/>

                    <div class=" p-3 m-2 ">
                        <h5>Short biography</h5>

                            {{adventure.biography}}
                    </div>

                    

                </div>

            </div>
        </div>
        <br/>
        <hr/>
        <div class="row col-12" style="alignment: center;">
                <div class="px-3" style="background: #f8f2ec;">
                    <p style="font-size: 25px;"><h4>Reservations:</h4></p>
                    <div class="pl-6">
                        Start date:
                        <input type="date" name="startDate" placeholder="dd-mm-yyyy">
                            End date:
                            <input type="date" name="endDate" placeholder="dd-mm-yyyy">
                                Person number:
                                <input type="number" name="numPeople" min="1" max="10" style="width: 50px">
                            <div class="res_button">
                                <button type="button" class="btn ">Reserve</button>
                            </div>
                    </div>
                    
                </div>

        </div>
        <hr/>
        <div class="row ">
            <div class="col-12" style="padding-left: 15px;alignment: center;background: #f8f2ec;">
                <p id="quick_heading">Quick reservations - enormous discounts!</p>
                <div class="row p-3">
                    <div class="col-4 p-2 m-2 quick_res" v-for="q in adventure.quickReservations">
                        <div>
                            <p class="res_date">{{q.startDateTime}} - {{q.endDateTime}}</p>
                            <div class="discount">{{q.discount}}%</div>
                        </div>
                        <p><i class="fas fa-user-friends"></i> {{q.maxPersonNum}}</p>
                        $<span class="text-decoration-line-through">{{q.price}}</span>
                        $<span class="before_price">{{q.discountedPrice}}</span>
                        <div class="quick_res_btn">
                            <button type="button" class="btn">RESERVE</button>
                        </div>
                    </div>

                </div>    
            </div>
        </div>
    </div>
</section>
        `,
    mounted:function (){
        axios
            .get("api/adventures/2")
            .then(response => (this.adventure = response.data))

    }
});
