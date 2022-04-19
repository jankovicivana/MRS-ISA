Vue.component("cottage-profile",{
    data: function (){
        return{
            cottage: '',
            num_rooms: 0,
            num_beds: 0
        }
    },
    template:`
         <section class="profile_main py-lg-3">
            <div class="container cottage_profile px-4 px-lg-5 my-5">
                <div class="row align-items-center pt-5">
                    <div class="col-md-6">
                        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                              <div class="carousel-inner">
                                <div class="carousel-item active">
                                  <img class="d-block w-100" src="../images/cottage4.jpg" alt="First slide" style="border-radius: 2%">
                                </div>
                                <div class="carousel-item" v-for="i in cottage.images">
                                  <img class="d-block w-100" :src="i.path" alt="First slide" style="border-radius: 2%">
                                </div>
                              </div>
                              <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                              </a>
                              <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                              </a>
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
                            <p class="col-2" > <i class="fas fa-user-friends"></i> {{cottage.maxNumPerson}}</p>
                            <p class="col-2"><i class="fas fa-home"></i> {{num_rooms}}</p>
                            <p class="col-2"><i class="fas fa-bed"></i> {{num_beds}}</p>
                        </div>
                        <div class="row p-3">
                            <p style="font-size: 25px">Dodatne usluge</p>
                            <div class="services">
                            <p v-for="as in cottage.additionalServices"><i class="fas fa-check-circle p-2"></i>{{as.name}}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row p-3">
                    <div class="col-4" style="background: #f8f2ec;border-radius: 5%">
                    <p style="font-size: 25px;">Pravila ponasanja</p>
                         <div class="rules">
                            <p v-for="r in cottage.rules"><i class="fas fa-circle p-2"></i>{{r.rule}}</p>
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
                            <div class="col-4 p-3 m-2 quick_res" v-for="q in cottage.quickReservations">
                                <div>
                                    <p class="res_date">{{q.startDateTime}} - {{q.endDateTime}}</p>
                                    <div class="discount">{{q.discount}}%</div>
                                </div>
                                <p><i class="fas fa-user-friends"></i> {{q.maxPersonNum}}</p>
                                $<span class="text-decoration-line-through">{{q.price}}</span>
                                $<span class="before_price">{{q.discountedPrice}}</span>
                                <div class="quick_res_btn"><button type="button" class="btn">REZERVISI</button></div>
                            </div>
                            
                            
                            
                        </div>
                        
                    </div>
                </div>
            </div>
        </section>

    `,
    mounted:function (){
        const sumFuncy = async (a,b) => a+b;

        axios
            .get("api/cottages/1")
            .then(response => (this.cottage = response.data,this.num_rooms=response.data.rooms.length,response.data.rooms.forEach(async (room) => {this.num_beds=await sumFuncy(this.num_beds,room.bedNumber)})))

    }


});