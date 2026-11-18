<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { api, errText } from '../api'

const categories = ['HOUSING', 'SECONDHAND', 'EVENT', 'HELP', 'LANGUAGE']
const router = useRouter()
const f = ref({ category: 'HOUSING', title: '', content: '', price: '', city: '' })
const err = ref('')

async function submit() {
  err.value = ''
  try {
    const body = {
      category: f.value.category,
      title: f.value.title.trim(),
      content: f.value.content.trim(),
      price: f.value.price === '' ? null : Number(f.value.price),
      city: f.value.city.trim() || null,
    }
    const { data } = await api.post('/posts', body)
    router.push('/posts/' + data.id)
  } catch (e) {
    err.value = errText(e)
  }
}
</script>

<template>
  <h1>New post</h1>
  <div class="narrow wide">
    <p v-if="err" class="err">{{ err }}</p>
    <label>Category
      <select v-model="f.category">
        <option v-for="c in categories" :key="c" :value="c">{{ c }}</option>
      </select>
    </label>
    <label>Title (3 to 100 characters)<input v-model="f.title" maxlength="100" /></label>
    <label>Description<textarea v-model="f.content" rows="6" maxlength="5000"></textarea></label>
    <label>Price (optional)<input v-model="f.price" type="number" min="0" step="0.01" /></label>
    <label>City (optional)<input v-model="f.city" maxlength="50" /></label>
    <button :disabled="f.title.trim().length < 3 || !f.content.trim()" @click="submit">Publish post</button>
  </div>
</template>
