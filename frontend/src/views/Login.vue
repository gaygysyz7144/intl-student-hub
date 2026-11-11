<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { api, auth, errText } from '../api'

const router = useRouter()
const mode = ref('login')
const f = ref({ username: '', email: '', password: '', nationality: '', city: '' })
const err = ref('')
const ok = ref('')

async function submit() {
  err.value = ''
  ok.value = ''
  try {
    if (mode.value === 'register') {
      await api.post('/auth/register', f.value)
      ok.value = 'Account created. Log in to continue.'
      mode.value = 'login'
      return
    }
    const { data } = await api.post('/auth/login', { username: f.value.username, password: f.value.password })
    auth.token = data.token
    auth.user = data.user
    router.push('/posts')
  } catch (e) {
    err.value = errText(e)
  }
}
</script>

<template>
  <div class="narrow">
    <h1>{{ mode === 'login' ? 'Log in' : 'Create account' }}</h1>
    <p class="muted">Find rooms, jobs, study help and people in your city.</p>
    <p v-if="ok" class="ok">{{ ok }}</p>
    <p v-if="err" class="err">{{ err }}</p>
    <label>Username<input v-model="f.username" autocomplete="username" /></label>
    <label v-if="mode === 'register'">Email<input v-model="f.email" type="email" /></label>
    <label>Password<input v-model="f.password" type="password" autocomplete="current-password" @keyup.enter="submit" /></label>
    <template v-if="mode === 'register'">
      <label>Nationality<input v-model="f.nationality" /></label>
      <label>City<input v-model="f.city" /></label>
    </template>
    <button @click="submit">{{ mode === 'login' ? 'Log in' : 'Create account' }}</button>
    <button class="ghost" @click="mode = mode === 'login' ? 'register' : 'login'">
      {{ mode === 'login' ? 'I need an account' : 'I already have an account' }}
    </button>
  </div>
</template>
