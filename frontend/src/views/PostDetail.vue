<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { api, auth, errText } from '../api'

const id = useRoute().params.id
const post = ref(null)
const tags = ref([])
const msg = ref('')
const err = ref('')
const info = ref('')

onMounted(async () => {
  try {
    post.value = (await api.get(`/posts/${id}`)).data
  } catch (e) {
    err.value = errText(e)
    return
  }
  try {
    tags.value = (await api.get(`/posts/${id}/tags`)).data
  } catch (e) { /* tags are optional */ }
})

async function fav(add) {
  err.value = ''
  info.value = ''
  try {
    if (add) await api.post(`/favorites/${id}`)
    else await api.delete(`/favorites/${id}`)
    info.value = add ? 'Added to favorites.' : 'Removed from favorites.'
  } catch (e) {
    err.value = errText(e)
  }
}

async function send() {
  err.value = ''
  info.value = ''
  try {
    await api.post('/messages', { receiverId: post.value.authorId, content: msg.value })
    msg.value = ''
    info.value = 'Message sent.'
  } catch (e) {
    err.value = errText(e)
  }
}
</script>

<template>
  <RouterLink to="/posts" class="muted">Back to posts</RouterLink>
  <p v-if="err" class="err">{{ err }}</p>
  <p v-if="info" class="ok">{{ info }}</p>
  <article v-if="post">
    <h1>{{ post.title }}</h1>
    <p class="muted">
      {{ post.category }} – {{ post.city || 'No city' }}
      <span v-if="post.price != null"> – {{ post.price }}</span>
      – by {{ post.authorName }}
    </p>
    <p class="body">{{ post.content }}</p>
    <p v-if="tags.length">
      <span v-for="t in tags" :key="t.id" class="tag">{{ t.name }}</span>
    </p>
    <div class="row">
      <button @click="fav(true)">Add to favorites</button>
      <button class="ghost" @click="fav(false)">Remove from favorites</button>
    </div>
    <div v-if="post.authorId !== auth.user?.id" class="box">
      <h2>Message {{ post.authorName }}</h2>
      <textarea v-model="msg" rows="3" maxlength="1000" placeholder="Write your message"></textarea>
      <button :disabled="!msg.trim()" @click="send">Send message</button>
    </div>
  </article>
</template>
