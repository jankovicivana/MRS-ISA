Vue.component("cottage-profile",{
    data: function (){
        return{
            cottage: ''
        }
    },
    template:`
         <section class="profile_main py-lg-3">
            <div class="container cottage_profile px-4 px-lg-5 my-5">
                <div class="row align-items-center pt-5">
                    <div class="col-md-6">
                        <img class="main_photo " :src="cottage.mainPhotoUrl" alt="Cottage main photo" width="100%"/>
                        <div class="row thumbs pt-3 ">
                            <span v-for="i in cottage.images" class="side_photo col-3 px-1"><img :src="i" alt="Cottage photo1" class="img-responsive" width="130px" height="130px"></span>
                        </div>
                    </div>
                    <div class="col-md-6" >
                        <div class="row m-2">
                            <div class="col-9 fw-bolder" style="font-size: 35px">{{cottage.name}}</div>
                            <div class="col-3 pt-3">Ocena 5 <i class="fas fa-star" style="color: black"></i></div>
                        </div>
                        
                        <div class="fs-5 m-3">
                            Price: $<span>{{cottage.price}}</span>
                        </div>
                        <p class="lead p-3">{{cottage.description}}</p>
                        <div class="row number_info p-3">
                            <p class="col-2" > <i class="fas fa-user-friends"></i> {{cottage.numOfBeds}}</p>
                            <p class="col-2"><i class="fas fa-home"></i> {{cottage.numOfRooms}}</p>
                            <p class="col-2"><i class="fas fa-bed"></i> {{cottage.numOfBeds}}</p>
                        </div>
                        <div class="row p-3">
                            <p style="font-size: 25px">Dodatne usluge</p>
                            <div class="services">
                            <p v-for="as in cottage.additionalServices"><i class="fas fa-check-circle p-2"></i>{{as}}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row p-3">
                    <div class="col-4" style="background: #f8f2ec;border-radius: 5%">
                    <p style="font-size: 25px;">Pravila ponasanja</p>
                         <div class="rules">
                            <p v-for="r in cottage.rules"><i class="fas fa-circle p-2"></i>{{r}}</p>
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
                            <div class="col-4 p-3 m-2 quick_res" v-for="q in cottage.quickReservationsDTO">
                                <div>
                                    <p class="res_date">{{q.startDate}} - {{q.endDate}}</p>
                                    <div class="discount">{{q.discount}}%</div>
                                </div>
                                <p><i class="fas fa-user-friends"></i> {{q.maxPersonNum}}</p>
                                $<span class="text-decoration-line-through">{{q.beforePrice}}</span>
                                $<span class="before_price">{{q.afterPrice}}</span>
                                <div class="quick_res_btn"><button type="button" class="btn">REZERVISI</button></div>
                            </div>
                            
                            
                            
                        </div>
                        
                    </div>
                </div>
            </div>
        </section>

    `,
    mounted:function (){
        axios
            .get("api/cottages/1")
            .then(response => (this.cottage = response.data))

    }


});