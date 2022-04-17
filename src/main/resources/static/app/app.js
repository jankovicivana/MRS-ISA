const PostPage = { template: '<post-page></post-page>'}
const AddCottage = {template: '<add-cottage></add-cottage>'}
const cottageProfile = { template: '<cottage-profile></cottage-profile>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path:'/',component: PostPage},
        { path:'/addCottage',component: AddCottage},
        { path: '/cottageProfile',component: cottageProfile}
    ]
});

var app = new Vue({
    router,
    el: '#rentalApp'
});