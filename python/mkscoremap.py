import sys

if len(sys.argv) < 3:
    print 'Usage:<input> <output> [mincnt=30]'
    exit(-1)

mincnt = 30
if len(sys.argv) > 3:
    mincnt = int( sys.argv[3] )

lst = []
for l in open(sys.argv[1]):
    arr = l.split()
    lst.append( float(arr[2]) )

lst.sort()
lst.reverse()
arrl = []
tmpl = []
lastsc = -0.1

for sc in lst:
    if abs( lastsc-sc ) >= 1e-6:
        if len(tmpl) >= mincnt:
            arrl.append( tmpl )
            tmpl = []
        lastsc = sc
    tmpl.append( sc )

# add remaining term to tmpl
if len(tmpl) >= mincnt: 
    arrl.append( tmpl )
else:
    arrl[-1] += tmpl

arrl.reverse()
print '%d groups generated, start writing' % len(arrl)
fo = open( sys.argv[2], 'w' )
for tl in arrl:
    # take median
    fo.write( '%f\t%f\t%d\n' % ( tl[ len(tl)/2 ], tl[0], len(tl) ) )
fo.close()
