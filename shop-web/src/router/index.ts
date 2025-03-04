import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    component: () => import('@/App.vue'),
    children: [
      { path: '', name: 'products', component: () => import('@/views/ProductListView.vue') },
      { path: 'cart', name: 'cart', component: () => import('@/views/CartListView.vue') },
      { path: 'contact', name: 'contact', component: () => import('@/views/ContactFormView.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
})
export default router
