const PostPage = { template: '<post-page></post-page>'}
const AddCottage = {template: '<add-cottage></add-cottage>'}
const cottageProfile = { template: '<cottage-profile></cottage-profile>'}
const AddAdventure = {template: '<add-adventure></add-adventure>'}
const ClientProfile = {template: '<client-profile></client-profile>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path:'/',component: PostPage},
        { path: '/clientProfile', component: ClientProfile},
        { path:'/addCottage',component: AddCottage},
        { path: '/cottageProfile',component: cottageProfile},
        { path: '/addAdventure', component: AddAdventure}
    ]
});

var app = new Vue({
    router,
    el: '#rentalApp'
});