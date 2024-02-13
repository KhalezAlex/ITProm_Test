'use strict';

const profApi = Vue.resource("/api/professions{/id}");


const getIndex = (list, id) => {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('form-profs', {
    props: ['professions', 'profession'],
    data: function() {
        return {
            id: '',
            name: '',
            note: ''
        }
    },
    watch: {
        profession: function(newValue) {
            this.id = newValue.id;
            this.name = newValue.name;
            this.note = newValue.note;
        }
    },
    template:
        '<form class="form">' +
            '<input type="text" class="inputText" placeholder="название профессии" v-model="name">' +
            '<input type="text" class="inputText" placeholder="заметка" v-model="note">' +
            '<input type="button" class="inputSubmit" value="save" @click="save">' +
        '</form>',
    methods: {
        save: function() {
            const profession = {name: this.name, note: this.note};
            if (this.id) {
                profApi.update({id: this.id}, profession).then(result => {
                    result.json().then(data => {
                        const index = getIndex(this.professions, data.id);
                        this.professions.splice(index, 1, data);
                        this.flushData();
                    })
                })
            } else {
                profApi.save({}, profession).then(result => {
                    result.json().then(data => {
                        this.professions.push(data);
                        this.flushData();
                    })
                })
            }
        },
        flushData: function() {
            this.name = '';
            this.note = '';
            this.id = '';
        }
    }
});

Vue.component('row-profs', {
    props: ['profession', 'professions', 'editParent'],
    template:
        '<tr>' +
            '<td>{{profession.id}}</td>' +
            '<td>{{profession.name}}</td>' +
            '<td>{{profession.note}}</td>' +
            '<td><input type="button" class="del_edit_button" value="edit" @click="edit"></td>' +
            '<td><input type="button" class="del_edit_button" value="delete" @click="del"></td>' +
        '</tr>',
    methods: {
        edit: function() {
            this.editParent(this.profession);
        },
        del: function() {
            profApi.remove({id: this.profession.id}).then(result => {
                if (result.ok) {
                    this.professions.splice(this.professions.indexOf(this.profession), 1);
                }
            })
        }
    }
})

Vue.component('table-profs', {
   props: ['professions', 'editParent'],
   template:
       '<table class="table">' +
            '<tr>' +
               '<th>id</th>' +
               '<th>Название</th>' +
               '<th>Заметка</th>' +
               '<th>edit</th>' +
               '<th>delete</th>' +
            '</tr>' +
            '<row-profs v-for="profession in professions" :key="profession.id" :profession="profession"' +
                                                ' :professions="professions" :editParent="editParent"/>' +
       '</table>'
});

Vue.component('page-prof', {
    props: ['professions'],
    data: function() {
        return {
            profession: null
        }
    },
    template:
        '<div class="container">' +
            '<div class="modal">' +
                '<h3>Professions</h3>' +
                '<form-profs :professions="professions" :profession="profession"/>' +
                '<table-profs :professions="professions" :editParent="edit"/>' +
                '<form class="form">' +
                    '<span>Поиск по id</span>' +
                    '<input type="number" class="inputText" id="id" placeholder="введите id для поиска">' +
                    '<input type="button" class="inputSubmit" value="поиск" @click="findById">' +
                '</form>' +
            '</div>' +
        '</div>',
    created: function() {
        profApi.get().then(result => {
            result.json().then(data => {
                    data.forEach(profession => this.professions.push(profession));
                })
        })
    },
    methods: {
        edit: function(profession) {
            this.profession = profession;
        },
        findById: function() {
            Vue.http.get("/api/professions/" + document.getElementById("id").value).then(response => {
                response.json().then(data => {
                    alert(`{id: ${data.id}; name: ${data.name}}`);
                })
            })
        },
    }
})

const app = new Vue({
    el: '#profApp',
    template: '<page-prof :professions="professions"/>',
    data: {
        professions: []
    }
});