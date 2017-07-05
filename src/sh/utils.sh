function listjar () {
	rep=$1
	find $rep -name *.jar | xargs echo $* | tr ' ' ':'
}
