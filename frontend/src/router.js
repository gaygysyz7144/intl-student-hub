import { createRouter, createWebHistory } from 'vue-router'
import { auth } from './api'
import Login from './views/Login.vue'
import ListPage from './views/ListPage.vue'
import PostDetail from './views/PostDetail.vue'
import Inbox from './views/Inbox.vue'
import Tags from './views/Tags.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/posts' },
    { path: '/login', component: Login },
    { path: '/posts', component: ListPage, props: { url: '/posts', title: 'Posts', filters: true, empty: 'No posts match your search.' } },
    { path: '/posts/:id', component: PostDetail },
    { path: '/recommendations', component: ListPage, props: { url: '/recommendations', title: 'Recommended for you', empty: 'No recommendations yet. Choose interest tags first.' } },
    { path: '/favorites', component: ListPage, props: { url: '/favorites', title: 'My favorites', empty: 'You have no favorites yet.' } },
    { path: '/inbox', component: Inbox },
    { path: '/tags', component: Tags },
  ],
})

router.beforeEach((to) => {
  if (to.path !== '/login' && !auth.token) return '/login'
})

export default router
