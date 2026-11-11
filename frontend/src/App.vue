<script setup>
import { useRouter } from 'vue-router'
import { auth } from './api'

const router = useRouter()
function logout() {
  auth.token = null
  auth.user = null
  router.push('/login')
}
</script>

<template>
  <header v-if="auth.token" class="nav">
    <strong class="brand">Intl Student Hub</strong>
    <nav>
      <RouterLink to="/posts">Posts</RouterLink>
      <RouterLink to="/recommendations">For you</RouterLink>
      <RouterLink to="/favorites">Favorites</RouterLink>
      <RouterLink to="/inbox">Inbox</RouterLink>
      <RouterLink to="/tags">My tags</RouterLink>
    </nav>
    <span class="who">{{ auth.user?.username }}
      <button class="ghost" @click="logout">Log out</button>
    </span>
  </header>
  <main><RouterView :key="$route.fullPath" /></main>
</template>
