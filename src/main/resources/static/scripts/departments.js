'use strict';

const deptApi = Vue.resource("/api/departments{/id}");


const getIndex = (list, id) => {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('option-parentDept', {
    props: ['department', 'keyDepartment'],
    template:
        '<option v-if="department !== keyDepartment" class="inputText" ' +
                            'v-bind:value="department.id">{{department.name}}</option>'
})

Vue.component('form-dept', {
    props: ['departments', 'department'],
    data: function() {
        return {
            id: '',
            name: '',
            note: '',
            parentId: '',
            // parentName: ''
        }
    },
    watch: {
        department: function(newValue) {
            this.id = newValue.id;
            this.name = newValue.name;
            this.note = newValue.note;
            this.parentId = newValue.parentId;
            // this.parentName = newValue.parentName;
        }
    },
    template:
        '<form class="form">' +
            '<input type="text" class="inputText" placeholder="название отдела" v-model="name">' +
            '<input type="text" class="inputText" placeholder="заметка" v-model="note">' +
            '<span>Выберите родительский отдел (root - корневой. без родителя)</span>' +
            '<select v-model="parentId">' +
                '<option-parentDept v-for="dept in departments" :key="dept.id" :department="dept" :keyDepartment="department" />' +
                '<option v-bind:value="0">root</option>' +
            '</select>' +
            '<input type="button" class="inputSubmit" value="save" @click="save"/>' +
        '</form>',
    methods: {
        save: function() {
            const department = {name: this.name, note: this.note, parentId: this.parentId};
            if (this.id) {
                deptApi.update({id: this.id}, department).then(result => {
                    result.json().then(data => {
                        const index = getIndex(this.departments, data.id);
                        this.departments.splice(index, 1, data);
                        this.flushData();
                    })
                })
            } else {
                deptApi.save({}, department).then(result => {
                    result.json().then(data => {
                        this.departments.push(data);
                        this.flushData();
                    })
                })
            }
        },
        flushData: function() {
            this.name = '';
            this.note = '';
            this.id = '';
            this.parentId = '';
        }
    }
});

Vue.component('row-dept', {
    props: ['department', 'departments', 'editParent'],
    template:
        '<tr>' +
            '<td>{{department.id}}</td>' +
            '<td>{{department.name}}</td>' +
            '<td>{{department.note}}</td>' +
            '<td v-if="this.department.parentId != 0">{{department.parentId}}</td>' +
            '<td v-if="this.department.parentId == 0"></td>' +
            '<td><input type="button" class="del_edit_button" value="edit" @click="edit"></td>' +
            '<td><input type="button" class="del_edit_button" value="delete" @click="del"></td>' +
        '</tr>',
    methods: {
        edit: function() {
            this.editParent(this.department);
        },
        del: function() {
            deptApi.remove({id: this.department.id}).then(result => {
                if (result.ok) {
                    this.departments.splice(this.departments.indexOf(this.department), 1);
                }
            })
        }
    }
})

Vue.component('table-dept', {
    props: ['departments', 'editParent'],
    template:
        '<table class="table">' +
            '<tr>' +
                '<th>id</th>' +
                '<th>Название</th>' +
                '<th>Заметка</th>' +
                '<th>Родительский отдел</th>' +
                '<th>edit</th>' +
                '<th>delete</th>' +
            '</tr>' +
        '<row-dept v-for="department in departments" :key="department.id" :department="department"' +
                                            ' :departments="departments" :editParent="editParent"/>' +
        '</table>'
});

Vue.component('page-dept', {
    props: ['departments'],
    data: function() {
        return {
            department: null
        }
    },
    template:
        '<div class="container">' +
            '<div class="modal">' +
                '<h3>Departments</h3>' +
                '<form-dept :departments="departments" :department="department"/>' +
                '<table-dept :departments="departments" :editParent="edit"/>' +
                '<form class="form">' +
                    '<span>Поиск по id</span>' +
                    '<input type="number" class="inputText" id="id" placeholder="введите id для поиска">' +
                    '<input type="button" class="inputSubmit" value="поиск" @click="findById">' +
                '</form>' +
            '</div>' +
            '</div>',
    created: function() {
        deptApi.get().then(result => {
            result.json().then(data => {
                data.forEach(dept => this.departments.push(dept));
            })
        })
    },
    methods: {
        findById: function() {
            Vue.http.get("/api/departments/" + document.getElementById("id").value).then(response => {
                response.json().then(data => {
                    alert(`{id: ${data.id}; name: ${data.name}}`);
                })
            })
        },
        edit: function(department) {
            this.department = department;
        }
    }
})


const app = new Vue({
    el: '#deptApp',
    template: '<page-dept :departments="departments"/>',
    data: {
        departments: []
    }
});