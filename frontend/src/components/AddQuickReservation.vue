<script>
import axios from "axios";
import router from "../router";
import AdventureProfile from "./AdventureProfile";

export default {
  name: 'AddQuickReservation',
  props: ['id'],
  methods: {

    addQuickReservation:function (){
      let idn = this.id;
      console.log(idn)
      let start_date = this.$refs.start_date_input.value
      let end_date = this.$refs.end_date_input.value
      let expiration_date = this.$refs.expiration_date_input.value
      if(start_date>end_date){
        alert("End date must be after start date.")
        return;
      }
      if(expiration_date>start_date){
        alert("Expiration date must be before start date.")
        return;
      }
      let price = this.$refs.price_input.value
      let discount = this.$refs.discount_input.value
      let max_person_num = this.$refs.max_person_num.value

      this.info = {
        entId:idn,
        startDateTime: start_date,
        endDateTime: end_date,
        expirationDateTime:expiration_date,
        maxPersonNum:max_person_num,
        price: price,
        discount: discount,
        discountedPrice: price - price*discount/100,
        };

      axios.post(process.env.VUE_APP_SERVER_PORT+"/api/quickReservation/addQuickReservation",this.info)
        .then(response => {
          alert("Addition is successfull!")
          router.go(AdventureProfile)

        }).catch(function error(error) {
        alert(error.response.data);
      });


    }
  },
};
</script>

<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <div class="modal"
           role="dialog"
           aria-labelledby="modalTitle"
           aria-describedby="modalDescription"
           style="background-image: url('https://images.unsplash.com/photo-1632862316541-0b75b0126544?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fGZpc2hpbmd8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60'); background-size: 100% 100%;"
      >
        <div class="mask d-flex align-items-center pt-3 h-100 gradient-custom-3">
          <div class="container h-100" >
            <div class="row d-flex justify-content-center align-items-center h-100" >
              <div class="col-12 col-md-9 col-lg-7 col-xl-6" >
                <div class="card" style="border-radius: 15px; background: #ecd9c6;   ">
                  <div class="card-body p-5">
                    <h2 class="text-uppercase text-center mb-5" style=" color: #04414d; font-family: 'Bookman Old Style',serif;">Add new quick reservation</h2>
                    <form>
                      <hr/>
                      <h4>Date Info</h4>

                      <div class="form-outline mb-4">
                        <label class="label">Start date:</label>
                        <div>
                          <input class="form-control form-control-lg" ref="start_date_input" type="date"   placeholder="Start date input" />
                        </div>
                      </div>
                      <div class="form-outline mb-4">
                        <label class="label">End date:</label>
                        <div>
                          <input class="form-control form-control-lg" type="date" ref="end_date_input"   placeholder="End date input" />
                        </div>
                      </div>
                      <div class="form-outline mb-4">
                        <label class="label">Expiration date:</label>
                        <div>
                          <input class="form-control form-control-lg" type="date" ref="expiration_date_input"   placeholder="Expiration date input" />
                        </div>
                      </div>
                      <hr />
                      <h4>Price Details</h4>

                      <div class="form-outline mb-4">
                        <label class="label">Price:</label>
                        <div>
                          <input class="form-control form-control-lg" type="number"  ref="price_input" min="0" placeholder="Price input" />
                        </div>
                      </div>

                      <div class="form-outline mb-4">
                        <label class="label">Discount:</label>
                        <div>
                          <input class="form-control form-control-lg" type="number" ref="discount_input" min="0"  placeholder="Discount input" />
                        </div>
                      </div>

                      <hr />
                      <div class="form-outline mb-4">
                        <label class="label">Max person number:</label>
                        <div>
                          <input class="form-control form-control-lg" type="number" min="0"  ref="max_person_num" placeholder="Enter max person num" />
                        </div>
                      </div>
                      <div class="d-flex justify-content-center">
                        <button type="submit" v-on:click="addQuickReservation()" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body" style="background-color: #04414d;"><div style="color:white">Add</div></button>
                      </div>
                    </form>
                    <br/>

                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<style>
.modal-backdrop {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background: #FFFFFF;
  box-shadow: 2px 2px 20px 1px;
  overflow-x: auto;
  display: flex;
  flex-direction: column;
}

.modal-header,
.modal-footer {
  padding: 15px;
  display: flex;
}

.modal-header {
  position: relative;
  border-bottom: 1px solid #eeeeee;
  color: #4AAE9B;
  justify-content: space-between;
}

.modal-footer {
  border-top: 1px solid #eeeeee;
  flex-direction: column;
}

.modal-body {
  position: relative;
  padding: 20px 10px;
}

.btn-close {
  position: absolute;
  top: 0;
  right: 0;
  border: none;
  font-size: 20px;
  padding: 10px;
  cursor: pointer;
  font-weight: bold;
  color: #4AAE9B;
  background: transparent;
}

.btn-green {
  color: white;
  background: #4AAE9B;
  border: 1px solid #4AAE9B;
  border-radius: 2px;
}

.modal-fade-enter,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity .5s ease;
}
</style>
